package com.trivago.booking.repo

import com.trivago.booking.model.Reservation
import com.trivago.booking.model.ReservationReference

interface ReservationRepository {

    fun saveCustomer(customerName: String, customerMail:String): Int

    fun makeReservations(reservations: List<Reservation>, customerId: Int, reservationReference: ReservationReference)

    fun referenceExisting(reference : String): Boolean
}