package com.trivago.booking.service

import com.trivago.booking.model.Room
import com.trivago.booking.model.RoomGuests
import com.trivago.booking.repo.AvailabilityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AvailabilityServiceImpl : AvailabilityService {

    @Autowired
    private lateinit var availabilityRepository: AvailabilityRepository

    override fun retrieveAvailableRoomTypes(startDate: String, endDate: String, roomGuests: List<RoomGuests>?): List<Room> {
        val parsedStartDate = LocalDate.parse(startDate)
        val parsedEndDate = LocalDate.parse(endDate)
        val availableRoomTypes = mutableListOf<Room>()

        if (roomGuests != null && roomGuests.isNotEmpty()) {
            roomGuests.forEach { obj ->
                val roomTypes = availabilityRepository.retrieveAvailableRoomTypesByOccupancy(parsedStartDate, parsedEndDate, obj)

                roomTypes.forEach { roomType ->
                    roomType.roomGuests.adults = obj.adults
                    roomType.roomGuests.juniors = obj.juniors
                    roomType.roomGuests.babies = obj.babies
                }

                availableRoomTypes.addAll(roomTypes)
            }
        } else {
            availableRoomTypes.addAll(availabilityRepository.retrieveAvailableRoomTypes(parsedStartDate, parsedEndDate))
        }


        return availableRoomTypes
    }

    override fun retrieveAvailableRoom(startDate: LocalDate, endDate: LocalDate, roomTypeCode: String, roomGuests: RoomGuests): Room? {
        return availabilityRepository.retrieveAvailableRoom(startDate, endDate, roomTypeCode, roomGuests)
    }
}