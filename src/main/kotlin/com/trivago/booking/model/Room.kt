package com.trivago.booking.model

data class Room(val roomTypeCode: String, val roomTypeName: String, val occupancy: HotelGuests, val amount: Double, val roomsAvailable: Int?)