package com.trivago.booking.service

import com.trivago.booking.api.request.ReservationRequest

interface ReservationService {

    fun makeRoomBooking(reservationRequest: ReservationRequest): Any

    fun verifyBooking(reference: Int): Any
}