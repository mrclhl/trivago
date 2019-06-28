package com.trivago.booking.repo

import com.trivago.booking.model.HotelGuests
import com.trivago.booking.model.Room
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Connection
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
        
        const val isRoomTypeAvailableQuery: String = "SELECT rooms.roomCode, roomtype.roomName, roomtype.adults, roomtype.juniors, roomtype.babies, roomprice.price, COUNT(rooms.roomCode) " +
                "FROM rooms " +
                "  INNER JOIN roomtype ON rooms .roomCode = roomtype.roomCode " +
                "  INNER JOIN roomprice ON rooms .id = roomprice.roomid " +
                "  LEFT JOIN reservation ON rooms .id = reservation.roomId " +
                "WHERE reservation.roomId IS NULL " +
                "  AND roomtype.roomCode LIKE ? " +
                "  AND roomtype.adults >= ? " +
                "  AND roomtype.juniors >= ? " +
                "  AND roomtype.babies >= ? " +
                "      OR reservation.startDate NOT BETWEEN ? AND ? " +
                "         AND reservation.endDate NOT BETWEEN ? AND ? " +
                "GROUP BY rooms.roomCode, roomtype.roomName, roomtype.adults, roomtype.juniors, roomtype.babies, roomprice.price " +
                "ORDER BY roomprice.price ASC;"
    }

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    override fun retrieveAvailableRoomTypes(startDate: LocalDate, endDate: LocalDate): List<Room> {
        val rooms: MutableList<Room> = mutableListOf()

        jdbcTemplate.query({con: Connection ->
            val statement = con.prepareStatement(roomTypesQuery)
            statement.setObject(1, startDate)
            statement.setObject(2, endDate)
            statement.setObject(3, startDate)
            statement.setObject(4, endDate)
            statement
        }, {rs ->
            val roomCode = rs.getString("roomCode")
            val roomName = rs.getString("roomName")
            val adults = rs.getInt("adults")
            val juniors = rs.getInt("juniors")
            val babies = rs.getInt("babies")
            val price = rs.getDouble("price")
            val count = rs.getInt("count")

            rooms.add(Room(roomCode, roomName, HotelGuests(adults, babies, juniors), price, count))
        })

        return rooms
    }

    override fun roomTypeAvailable(startDate: LocalDate, endDate: LocalDate, roomTypeCode: String, occupancy: HotelGuests): Boolean {
        var isAvailable = false

        jdbcTemplate.query({con: Connection ->
            val statement = con.prepareStatement(isRoomTypeAvailableQuery)
            statement.setString(1, roomTypeCode)
            statement.setInt(2, occupancy.adults)
            statement.setInt(3, occupancy.juniors)
            statement.setInt(4, occupancy.babies)
            statement.setObject(5, startDate)
            statement.setObject(6, endDate)
            statement.setObject(7, startDate)
            statement.setObject(8, endDate)
            statement
        }, {rs ->
            isAvailable = rs.first()
        })

        return isAvailable
    }
}