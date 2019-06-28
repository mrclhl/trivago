package com.trivago.booking.model

data class Room(val roomId: Int?, val roomTypeCode: String, val roomTypeName: String, val occupancy: HotelGuests, val amount: Double, val availableRooms: Int?)