package com.trivago.booking.api.response

data class ReservationResponse(val reference: Int,
                               val totalAmount: Double,
                               val customerFullName: String,
                               val customerMail: String) : BaseResponse()