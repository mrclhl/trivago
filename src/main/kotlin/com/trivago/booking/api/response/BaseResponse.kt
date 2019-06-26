package com.trivago.booking.api.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.trivago.booking.model.Room

@JsonInclude(JsonInclude.Include.NON_NULL)
open class BaseResponse {

    constructor()

    constructor(startDate: String, endDate: String, roomTypes: List<Room>) {
        this.startDate = startDate
        this.endDate = endDate
        this.roomTypes = roomTypes
    }

    var startDate: String = ""
    var endDate: String = ""
    var roomTypes: List<Room> = listOf()
}