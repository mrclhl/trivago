package com.trivago.booking.api.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ReservationResponse(val reference: String,
                               val totalAmount: Double,
                               val customerFullName: String,
                               val customerMail: String) : BaseResponse()