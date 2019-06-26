package com.trivago.booking.model

data class RoomTypes(val rooms: List<Room>?)

data class Room(val roomTypeCode: String, val roomTypeName: String, val occupancy: HotelGuests, val amount: Double)