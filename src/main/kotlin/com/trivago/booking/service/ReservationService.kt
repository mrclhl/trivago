package com.trivago.booking.service

import com.trivago.booking.api.request.RoomType
import com.trivago.booking.model.Booking

interface ReservationService {

    fun makeReservation(startDate: String, endDate: String, customerFullName: String, customerMail: String, roomTypes: List<RoomType>): String

    fun retrieveReservation(reference: String): Booking
}