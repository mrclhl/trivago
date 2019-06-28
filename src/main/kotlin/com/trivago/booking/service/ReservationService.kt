package com.trivago.booking.service

import com.trivago.booking.api.request.ReservationRequest
import com.trivago.booking.model.ReservationReference

interface ReservationService {

    fun makeReservations(reservationRequest: ReservationRequest): ReservationReference?

    fun verifyBooking(reservationReference: ReservationReference): Any
}