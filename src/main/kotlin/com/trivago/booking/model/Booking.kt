package com.trivago.booking.model

data class Booking(var startDate: String, var endDate: String, var total: Double, val customerName: String, val customerMail: String, val rooms: List<Room>)