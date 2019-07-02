package com.trivago.booking.validator

import com.trivago.booking.api.request.RoomType
import com.trivago.booking.exceptions.RoomCodeMissingException
import com.trivago.booking.exceptions.RoomGuestMissingException

class RoomTypeValidator {

    companion object {
        fun isRequestValid(roomtypes: Array<RoomType>): Boolean {
            roomtypes.forEach { roomType ->
                if (roomType.roomTypeCode.isBlank()) throw RoomCodeMissingException("You need to specify the room code for the reservation.")

                val adults = roomType.occupancy.adults
                val juniors = roomType.occupancy.juniors
                val babies = roomType.occupancy.babies

                if (adults == null && juniors == null && babies == null) throw RoomGuestMissingException("Each room booking needs to contain at least one guest.")
            }

            return true
        }
    }
}