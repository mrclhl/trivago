package com.trivago.booking.api.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.trivago.booking.model.Room

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ReservationResponse(val reference: String,
                               val totalAmount: Double,
                               val customerFullName: String,
                               val customerMail: String,
                               private val reservationStartDate: String,
                               private val reservationEndDate: String,
                               private val reservationRoomTypes: List<Room>) : BaseResponse(reservationStartDate, reservationEndDate, reservationRoomTypes)