package com.trivago.booking.api.reservation

import com.trivago.booking.api.request.ReservationRequest
import com.trivago.booking.api.response.BaseResponse
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReservationController {

    companion object {
        const val ReservationEndpoint = "/reservation"
        const val VerificationEndpoint = "$ReservationEndpoint/verification"
    }

    @PostMapping(ReservationEndpoint, consumes = [(MimeTypeUtils.APPLICATION_JSON_VALUE)], produces = [(MimeTypeUtils.APPLICATION_JSON_VALUE)])
    fun reservation(@RequestBody reservationRequest: ReservationRequest): BaseResponse {
        reservationRequest.validateInput()

        return BaseResponse(reservationRequest.startDate, reservationRequest.endDate, emptyList())
    }

    @GetMapping(VerificationEndpoint, consumes = [(MimeTypeUtils.APPLICATION_JSON_VALUE)], produces = [(MimeTypeUtils.APPLICATION_JSON_VALUE)])
    fun verification(@RequestBody(required = true) reference: Int): BaseResponse {

        return BaseResponse("", "", emptyList())
    }
}