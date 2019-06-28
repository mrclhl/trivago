package com.trivago.booking.repo

interface ReservationRepository {

    fun saveCustomer(customerName: String, customerMail:String): Int
}