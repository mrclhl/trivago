package com.trivago.booking.api.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.trivago.booking.api.request.Occupancy

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RoomResponse(val roomId: Int?, val roomTypeCode: String, val roomTypeName: String, val occupancy: Occupancy, val amount: Double, val roomsAvailable: Int?)

