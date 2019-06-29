package com.trivago.booking.repo

import com.trivago.booking.model.Room
import com.trivago.booking.model.RoomGuests
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.ResultSet
import java.time.LocalDate

@Repository
class AvailabilityRepositoryImpl : AvailabilityRepository {

    companion object {
        const val roomTypesQuery: String = "SELECT " +
                "  rooms.roomCode, " +
                "  roomtype.roomName, " +
                "  roomtype.adults, " +
                "  roomtype.juniors, " +
                "  roomtype.babies, " +
                "  roomprice.price, " +
                "  COUNT(rooms.roomCode) " +
                "FROM rooms " +
                "  INNER JOIN roomtype ON rooms .roomCode = roomtype.roomCode " +
                "  INNER JOIN roomprice ON roomtype .roomCode = roomprice.roomCode " +
                "  LEFT JOIN reservation ON rooms .id = reservation.roomId " +
                "WHERE reservation.roomId IS NULL OR reservation.startDate NOT BETWEEN ? AND ? AND reservation.endDate NOT BETWEEN ? AND ? " +
                "GROUP BY rooms.roomCode, roomtype.roomName, roomtype.adults, roomtype.juniors, roomtype.babies, roomprice.price " +
                "ORDER BY roomprice.price ASC;"

        const val roomTypesByOccupancyQueryStart: String = "SELECT " +
                "  rooms.roomCode, " +
                "  roomtype.roomName, " +
                "  roomtype.adults, " +
                "  roomtype.juniors, " +
                "  roomtype.babies, " +
                "  roomprice.price, " +
                "  COUNT(rooms.roomCode) " +
                "FROM rooms " +
                "  INNER JOIN roomtype ON rooms.roomCode = roomtype.roomCode " +
                "  INNER JOIN roomprice ON roomtype.roomCode = roomprice.roomCode " +
                "  LEFT JOIN reservation ON rooms.id = reservation.roomId " +
                "WHERE " +
                "  reservation.roomId IS NULL "

        const val roomTypesByOccupancyQueryEnd: String = " OR reservation.startDate NOT BETWEEN ? AND ? AND reservation.endDate NOT BETWEEN ? AND ? " +
                "GROUP BY rooms.roomCode, roomtype.roomName, roomtype.adults, roomtype.juniors, roomtype.babies, roomprice.price " +
                "ORDER BY roomprice.price ASC;"

        const val isRoomTypeAvailableQueryStart: String = "SELECT " +
                "  rooms.id, " +
                "  rooms.roomCode, " +
                "  roomtype.roomName, " +
                "  roomtype.adults, " +
                "  roomtype.juniors, " +
                "  roomtype.babies, " +
                "  roomprice.price, " +
                "  COUNT(rooms.roomCode) " +
                "FROM rooms " +
                "  INNER JOIN roomtype ON rooms .roomCode = roomtype.roomCode " +
                "  INNER JOIN roomprice ON roomtype .roomCode = roomprice.roomCode " +
                "  LEFT JOIN reservation ON rooms .id = reservation.roomId " +
                "WHERE reservation.roomId IS NULL " +
                "  AND roomtype.roomCode LIKE ? "

        const val isRoomTypeAvailableQueryEnd: String = " OR reservation.startDate NOT BETWEEN ? AND ? " +
                " AND reservation.endDate NOT BETWEEN ? AND ? " +
                "GROUP BY rooms.id, rooms.roomCode, roomtype.roomName, roomtype.adults, roomtype.juniors, roomtype.babies, roomprice.price " +
                "ORDER BY roomprice.price ASC;"
    }

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    override fun retrieveAvailableRoomTypes(startDate: LocalDate, endDate: LocalDate): List<Room> {
        val rooms: MutableList<Room> = mutableListOf()

        jdbcTemplate.query({ con: Connection ->
            val statement = con.prepareStatement(roomTypesQuery)
            statement.setObject(1, startDate)
            statement.setObject(2, endDate)
            statement.setObject(3, startDate)
            statement.setObject(4, endDate)
            statement
        }, { rs ->
            val roomCode = rs.getString("roomCode")
            val roomName = rs.getString("roomName")
            val adults = rs.getInt("adults")
            val juniors = rs.getInt("juniors")
            val babies = rs.getInt("babies")
            val price = rs.getDouble("price")
            val count = rs.getInt("count")

            rooms.add(Room(null, roomCode, roomName, RoomGuests(adults, babies, juniors), price, count))
        })

        return rooms
    }

    override fun retrieveAvailableRoomTypesByOccupancy(startDate: LocalDate, endDate: LocalDate, roomGuests: RoomGuests): List<Room> {
        val rooms: MutableList<Room> = mutableListOf()
        val (adultsCondition, juniorsCondition, babiesCondition) = setupRoomGuestsQueryConditions(roomGuests)

        jdbcTemplate.query({ con: Connection ->
            val query = roomTypesByOccupancyQueryStart + adultsCondition + juniorsCondition + babiesCondition + roomTypesByOccupancyQueryEnd
            val statement = con.prepareStatement(query)
            statement.setObject(1, startDate)
            statement.setObject(2, endDate)
            statement.setObject(3, startDate)
            statement.setObject(4, endDate)
            statement
        }, { rs ->
            saveRoom(rs, rooms)

            while (rs.next()) {
                saveRoom(rs, rooms)
            }
        })

        return rooms
    }

    override fun retrieveAvailableRoom(startDate: LocalDate, endDate: LocalDate, roomTypeCode: String, roomGuests: RoomGuests): Room? {
        var room: Room? = null
        val (adultsCondition, juniorsCondition, babiesCondition) = setupRoomGuestsQueryConditions(roomGuests)

        jdbcTemplate.query({ con: Connection ->
            val query = isRoomTypeAvailableQueryStart + adultsCondition + juniorsCondition + babiesCondition + isRoomTypeAvailableQueryEnd
            val statement = con.prepareStatement(query)
            statement.setString(1, roomTypeCode)
            statement.setObject(2, startDate)
            statement.setObject(3, endDate)
            statement.setObject(4, startDate)
            statement.setObject(5, endDate)
            statement
        }, { rs ->
            val roomId = rs.getInt("id")
            val roomCode = rs.getString("roomCode")
            val roomName = rs.getString("roomName")
            val adults = rs.getInt("adults")
            val juniors = rs.getInt("juniors")
            val babies = rs.getInt("babies")
            val price = rs.getDouble("price")

            room = Room(roomId, roomCode, roomName, RoomGuests(adults, babies, juniors), price, null)
        })

        return room
    }

    private fun setupRoomGuestsQueryConditions(roomGuests: RoomGuests): Triple<String, String, String> {
        val adultsCondition = if (roomGuests.adults != null) " AND roomtype.adults >= " + roomGuests.adults else ""
        val juniorsCondition = if (roomGuests.juniors != null) " AND roomtype.juniors >= " + roomGuests.juniors else ""
        val babiesCondition = if (roomGuests.babies != null) " AND roomtype.babies >= " + roomGuests.babies else ""

        return Triple(adultsCondition, juniorsCondition, babiesCondition)
    }

    private fun saveRoom(rs: ResultSet, rooms: MutableList<Room>) {
        val roomCode = rs.getString("roomCode")
        val roomName = rs.getString("roomName")
        val adults = rs.getInt("adults")
        val juniors = rs.getInt("juniors")
        val babies = rs.getInt("babies")
        val price = rs.getDouble("price")
        val count = rs.getInt("count")

        rooms.add(Room(null, roomCode, roomName, RoomGuests(adults, babies, juniors), price, count))
    }
}