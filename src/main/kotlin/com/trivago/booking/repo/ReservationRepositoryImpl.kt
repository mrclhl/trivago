package com.trivago.booking.repo

import com.trivago.booking.model.Reservation
import com.trivago.booking.model.ReservationReference
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class ReservationRepositoryImpl : ReservationRepository {

    companion object {
        const val saveCustomerQuery: String = "INSERT INTO customer (customerName, customerMail) VALUES (:customerName, :customerMail);"
        const val makeReservationQuery: String = "INSERT INTO reservation (startDate, endDate, customerId, roomId, bookingTime, reference) VALUES (?, ?, ?, ?, now(), ?);"
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

    override fun makeReservations(reservations: List<Reservation>, customerId: Int, reservationReference: ReservationReference) {
        jdbcTemplate.batchUpdate(makeReservationQuery, reservations, reservations.size) { ps, (startDate, endDate, roomId) ->
            ps.setObject(1, startDate)
            ps.setObject(2, endDate)
            ps.setInt(3, customerId)
            ps.setInt(4, roomId)
            ps.setString(5, reservationReference.reference)
        }
    }

    override fun referenceExisting(reference: String): Boolean {
        TODO()
    }
}