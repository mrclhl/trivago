package com.trivago.booking.model

data class Room(val roomId: Int?, val roomTypeCode: String, val roomTypeName: String, var roomGuests: RoomGuests, val amount: Double, val availableRooms: Int?)