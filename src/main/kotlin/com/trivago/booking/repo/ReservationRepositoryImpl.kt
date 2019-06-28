package com.trivago.booking.repo

import com.trivago.booking.model.Booking
import com.trivago.booking.model.HotelGuests
import com.trivago.booking.model.Reservation
import com.trivago.booking.model.Room
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.ResultSet

@Repository
class ReservationRepositoryImpl : ReservationRepository {

    companion object {
        const val saveCustomerQuery: String = "INSERT INTO customer (customerName, customerMail) VALUES (:customerName, :customerMail);"
        const val makeReservationQuery: String = "INSERT INTO reservation (startDate, endDate, adults, juniors, babies, customerId, roomId, bookingTime, reference) VALUES (?, ?, ?, ?, ?, ?, ?, now(), ?);"
        const val reservationExistingQuery: String = "SELECT * FROM reservation WHERE reference LIKE ?;"
        const val retrieveBookingQuery: String = "SELECT customer.customerName, customer.customerMail, reservation.startDate, reservation.endDate, rooms.roomCode, roomtype.roomName, reservation.adults, reservation.juniors, reservation.babies, roomprice.price " +
                "FROM reservation " +
                "  INNER JOIN rooms ON reservation .roomId = rooms.id " +
                "  INNER JOIN roomtype ON rooms .roomCode = roomtype.roomCode " +
                "  INNER JOIN roomprice ON roomtype .roomCode = roomprice.roomCode " +
                "  INNER JOIN customer ON reservation .customerId = customer.id " +
                "WHERE reservation.reference LIKE ?;"
    }

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var namedParameterJdbcTemplate: NamedParameterJdbcTemplate

    override fun saveCustomer(customerName: String, customerMail:String): Int {
        val generatedKeyHolder = GeneratedKeyHolder()

        val mapSqlParameterSource = MapSqlParameterSource()
        mapSqlParameterSource.addValue("customerName", customerName)
        mapSqlParameterSource.addValue("customerMail", customerMail)
        namedParameterJdbcTemplate.update(saveCustomerQuery, mapSqlParameterSource, generatedKeyHolder, arrayOf("id"))

        return generatedKeyHolder.key as Int
    }

    override fun makeReservation(reservations: List<Reservation>, customerId: Int, reservationReference: String) {
        jdbcTemplate.batchUpdate(makeReservationQuery, reservations, reservations.size) { ps, (startDate, endDate, adults, juniors, babies, roomId) ->
            ps.setObject(1, startDate)
            ps.setObject(2, endDate)
            ps.setInt(3, adults)
            ps.setInt(4, juniors)
            ps.setInt(5, babies)
            ps.setInt(6, customerId)
            ps.setInt(7, roomId)
            ps.setString(8, reservationReference)
        }
    }

    override fun referenceAlreadyExisting(reference: String): Boolean {
        var referenceExisting = false

        jdbcTemplate.query({con: Connection ->
            val statement = con.prepareStatement(reservationExistingQuery)
            statement.setString(1, reference)
            statement
        }, {rs ->
            referenceExisting = rs.first()
        })

        return referenceExisting
    }

    override fun retrieveBooking(reference: String): Booking? {
        var booking: Booking? = null

        jdbcTemplate.query({con: Connection ->
            val statement = con.prepareStatement(retrieveBookingQuery)
            statement.setString(1, reference)
            statement
        }, {rs ->
            // must be same for all rooms for the given reference
            val customerName = rs.getString("customerName")
            val customerMail = rs.getString("customerMail")
            val startDate = rs.getString("startDate")
            val endDate = rs.getString("endDate")
            val rooms = mutableListOf<Room>()
            saveRoom(rs, rooms)

            while(rs.next()) {
                saveRoom(rs, rooms)
            }

            booking = Booking(startDate, endDate, 0.0, customerName, customerMail, rooms)
        })

        return booking
    }

    private fun saveRoom(rs: ResultSet, rooms: MutableList<Room>) {
        // can be different for each booked room
        val roomCode = rs.getString("roomCode")
        val roomName = rs.getString("roomName")
        val adults = rs.getInt("adults")
        val juniors = rs.getInt("juniors")
        val babies = rs.getInt("babies")
        val price = rs.getDouble("price")
        rooms.add(Room(null, roomCode, roomName, HotelGuests(adults, juniors, babies), price, null))
    }
}