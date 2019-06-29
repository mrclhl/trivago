package com.trivago.booking.api.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.trivago.booking.api.request.Occupancy
import com.trivago.booking.model.Room

@JsonInclude(JsonInclude.Include.NON_NULL)
open class BaseResponse(var startDate: String, var endDate: String, roomTypes: List<Room>) {

    var roomTypes: List<RoomResponse> = listOf()

    private fun Room.toRoomResponse() = RoomResponse(
            roomId = roomId,
            roomTypeCode = roomTypeCode,
            roomTypeName = roomTypeName,
            occupancy = Occupancy(roomGuests.adults, roomGuests.juniors, roomGuests.babies),
            amount = amount,
            roomsAvailable = roomsAvailable
    )

    init {
        this.roomTypes = roomTypes.map { room -> room.toRoomResponse()}
    }
}