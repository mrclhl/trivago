package com.trivago.booking.repo

import com.trivago.booking.model.Booking
import com.trivago.booking.model.Reservation

interface ReservationRepository {

    fun saveCustomer(customerName: String, customerMail:String): Int

    fun makeReservation(reservations: List<Reservation>, customerId: Int, reservationReference: String)

    fun referenceAlreadyExisting(reference : String): Boolean

    fun retrieveBooking(reference: String): Booking?
}