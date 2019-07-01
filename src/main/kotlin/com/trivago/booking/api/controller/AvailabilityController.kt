package com.trivago.booking.api.controller

import com.trivago.booking.api.request.AvailabilityRequest
import com.trivago.booking.api.request.Occupancy
import com.trivago.booking.api.response.BaseResponse
import com.trivago.booking.model.RoomGuests
import com.trivago.booking.service.AvailabilityService
import com.trivago.booking.service.TimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AvailabilityController {

    companion object {
        const val AvailabilityEndpoint = "/availability"
    }

    @Autowired
    private lateinit var availabilityService: AvailabilityService

    @Autowired
    private lateinit var timeService: TimeService

    @PostMapping(AvailabilityEndpoint, consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun roomAvailability(@RequestBody availabilityRequest: AvailabilityRequest): BaseResponse{
        availabilityRequest.areDatesValid(timeService)
        val startDate = availabilityRequest.startDate
        val endDate = availabilityRequest.endDate
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