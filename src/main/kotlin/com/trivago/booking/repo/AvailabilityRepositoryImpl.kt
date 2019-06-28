package com.trivago.booking.repo

import com.trivago.booking.model.HotelGuests
import com.trivago.booking.model.Room
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class AvailabilityRepositoryImpl : AvailabilityRepository {

    companion object {
        const val roomTypesQuery: String = "SELECT rooms.roomCode, roomtype.roomName, roomtype.adults, roomtype.juniors, roomtype.babies, roomprice.price, COUNT(rooms.roomCode) " +
                "FROM rooms " +
                "  INNER JOIN roomtype ON rooms .roomCode = roomtype.roomCode " +
                "  INNER JOIN roomprice ON rooms .id = roomprice.roomid " +
                "  LEFT JOIN reservation ON rooms .id = reservation.roomId " +
                "WHERE reservation.roomId IS NULL OR reservation.startDate NOT BETWEEN ? AND ? AND reservation.endDate NOT BETWEEN ? AND ? " +
                "GROUP BY rooms.roomCode, roomtype.roomName, roomtype.adults, roomtype.juniors, roomtype.babies, roomprice.price " +
                "ORDER BY roomprice.price ASC;"
    }

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    override fun retrieveAvailableRoomTypes(startDate: String, endDate: String): List<Room> {
        val rooms: MutableList<Room> = mutableListOf()

        val parsedStartDate = LocalDate.parse(startDate)
        val parsedEndDate = LocalDate.parse(endDate)

        jdbcTemplate.query(roomTypesQuery, arrayOf(parsedStartDate, parsedEndDate, parsedStartDate, parsedEndDate) ,{ rs ->
            val roomCode = rs.getString("roomCode")
            val roomName = rs.getString("roomName")
            val adults = rs.getInt("adults")
            val juniors = rs.getInt("juniors")
            val babies = rs.getInt("babies")
            val price = rs.getDouble("price")
            val count = rs.getInt("count")
            rooms.add(Room(roomCode, roomName, HotelGuests(adults, babies, juniors), price, count))})

        return rooms
    }
}