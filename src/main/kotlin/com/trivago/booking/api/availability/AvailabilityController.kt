package com.trivago.booking.api.availability

import com.trivago.booking.api.request.AvailabilityRequest
import org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AvailabilityController {

    companion object {
        const val AvailabilityEndpoint = "/availability"
    }

    @PostMapping(AvailabilityEndpoint, consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun roomAvailability(@RequestBody availabilityRequest: AvailabilityRequest) {

    }
}