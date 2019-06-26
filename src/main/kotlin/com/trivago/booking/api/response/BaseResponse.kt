package com.trivago.booking.api.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.trivago.booking.model.Room

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseResponse(val startDate: String, val endDate: String, val roomTypes: List<Room>)