package com.trivago.booking.api.controller

import com.trivago.booking.api.request.ReservationRequest
import com.trivago.booking.api.response.ReservationResponse
import com.trivago.booking.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.GetMapping
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
    fun reservation(@RequestBody reservationRequest: ReservationRequest): String? {
        reservationRequest.validateInput()

        val reservationReference = reservationService.makeReservations(reservationRequest)

        return reservationReference.toString()
    }

    @GetMapping(VerificationEndpoint, consumes = [(MimeTypeUtils.APPLICATION_JSON_VALUE)], produces = [(MimeTypeUtils.APPLICATION_JSON_VALUE)])
    fun verification(@RequestBody(required = true) reference: Int): ReservationResponse {

        val reservationResponse = ReservationResponse(1, 200.0, "", "")
        reservationResponse.startDate = ""
        reservationResponse.endDate = ""

        return reservationResponse
    }
}