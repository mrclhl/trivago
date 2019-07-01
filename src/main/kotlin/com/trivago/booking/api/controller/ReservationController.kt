package com.trivago.booking.api.controller

import com.trivago.booking.api.request.ReferenceRequest
import com.trivago.booking.api.request.ReservationRequest
import com.trivago.booking.api.response.ReservationResponse
import com.trivago.booking.service.ReservationService
import com.trivago.booking.service.TimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReservationController(@Autowired private val reservationService: ReservationService,
                            private val timeService: TimeService) {

    companion object {
        const val ReservationEndpoint = "/reservation"
        const val VerificationEndpoint = "$ReservationEndpoint/verification"
    }

    @PostMapping(ReservationEndpoint, consumes = [(MimeTypeUtils.APPLICATION_JSON_VALUE)], produces = [(MimeTypeUtils.APPLICATION_JSON_VALUE)])
    fun reservation(@RequestBody reservationRequest: ReservationRequest): ReservationResponse {
        reservationRequest.validateInput(timeService)
        val startDate = reservationRequest.startDate
        val endDate = reservationRequest.endDate
        val customerFullName = reservationRequest.customerFullName
        val customerMail = reservationRequest.customerMail
        val roomTypes = reservationRequest.roomTypes.toList()

        val reference = reservationService.makeReservation(startDate, endDate, customerFullName, customerMail, roomTypes)
        val booking = reservationService.retrieveReservation(reference)

        val reservationResponse = ReservationResponse(reference, booking.total, booking.customerName, booking.customerMail,
                booking.startDate, booking.endDate, booking.rooms)

        return reservationResponse
    }

    @PostMapping(VerificationEndpoint, consumes = [(MimeTypeUtils.APPLICATION_JSON_VALUE)], produces = [(MimeTypeUtils.APPLICATION_JSON_VALUE)])
    fun verification(@RequestBody(required = true) referenceRequest: ReferenceRequest): ReservationResponse {
        referenceRequest.validateInput()

        val booking = reservationService.retrieveReservation(referenceRequest.reference)

        val reservationResponse = ReservationResponse(referenceRequest.reference, booking.total, booking.customerName, booking.customerMail,
                booking.startDate, booking.endDate, booking.rooms)

        return reservationResponse
    }
}