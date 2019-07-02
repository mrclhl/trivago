package com.trivago.booking.api.controller

import com.trivago.booking.api.request.AvailabilityRequest
import com.trivago.booking.api.request.Occupancy
import com.trivago.booking.api.response.BaseResponse
import com.trivago.booking.model.RoomGuests
import com.trivago.booking.service.AvailabilityService
import com.trivago.booking.service.TimeService
import com.trivago.booking.validator.DateValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AvailabilityController(@Autowired private val availabilityService: AvailabilityService,
                             private val timeService: TimeService) {

    companion object {
        const val AvailabilityEndpoint = "/availability"
    }

    @PostMapping(AvailabilityEndpoint, consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun roomAvailability(@RequestBody availabilityRequest: AvailabilityRequest): BaseResponse{
        val startDate = availabilityRequest.startDate
        val endDate = availabilityRequest.endDate
        DateValidator.areDatesValid(startDate, endDate, timeService)
        val occupancy = availabilityRequest.occupancy
        val roomGuests: List<RoomGuests>? = occupancy?.map { occ -> occ.toHotelGuests() }

        val availableRoomTypes = availabilityService.retrieveAvailableRoomTypes(startDate, endDate, roomGuests)

        return BaseResponse(startDate, endDate, availableRoomTypes)
    }

    fun Occupancy.toHotelGuests() = RoomGuests(
            adults = adults,
            juniors = juniors,
            babies = babies
    )
}