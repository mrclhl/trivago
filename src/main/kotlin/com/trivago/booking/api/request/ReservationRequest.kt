package com.trivago.booking.api.request

class ReservationRequest : BaseRequest() {

    var customerFullName: String = ""
    var customerMail: String = ""
    var roomTypes: Array<RoomType> = arrayOf()
}