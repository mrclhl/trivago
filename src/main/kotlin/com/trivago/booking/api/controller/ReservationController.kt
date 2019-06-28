package com.trivago.booking.api.controller

import com.trivago.booking.api.request.ReferenceRequest
import com.trivago.booking.api.request.ReservationRequest
import com.trivago.booking.api.response.ReservationResponse
import com.trivago.booking.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReservationController {

    companion object {
        const val ReservationEndpoint = "/reservation"
        const val VerificationEndpoint = "${ReservationEndpoint}/verification"
    }

    @Autowired
    private lateinit var reservationService: ReservationService

    @PostMapping(ReservationEndpoint, consumes = [(MimeTypeUtils.APPLICATION_JSON_VALUE)], produces = [(MimeTypeUtils.APPLICATION_JSON_VALUE)])
    fun reservation(@RequestBody reservationRequest: ReservationRequest): ReservationResponse {
        reservationRequest.validateInput()

        val reference = reservationService.makeReservation(reservationRequest)
        val booking = reservationService.retrieveBooking(reference)

        val reservationResponse = ReservationResponse(reference, booking.total, booking.customerName, booking.customerMail)
        reservationResponse.startDate = booking.startDate
        reservationResponse.endDate = booking.endDate
        reservationResponse.roomTypes = booking.rooms

        return reservationResponse
    }

    @PostMapping(VerificationEndpoint, consumes = [(MimeTypeUtils.APPLICATION_JSON_VALUE)], produces = [(MimeTypeUtils.APPLICATION_JSON_VALUE)])
    fun verification(@RequestBody(required = true) referenceRequest: ReferenceRequest): ReservationResponse {
        val booking = reservationService.retrieveBooking(referenceRequest.reference)

        val reservationResponse = ReservationResponse(referenceRequest.reference, booking.total, booking.customerName, booking.customerMail)
        reservationResponse.startDate = booking.startDate
        reservationResponse.endDate = booking.endDate
        reservationResponse.roomTypes = booking.rooms

        return reservationResponse
    }
}