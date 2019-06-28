package com.trivago.booking.service

import com.trivago.booking.api.request.ReservationRequest
import com.trivago.booking.model.Booking

interface ReservationService {

    fun makeReservation(reservationRequest: ReservationRequest): String

    fun retrieveBooking(reference: String): Booking
}