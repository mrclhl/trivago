package com.trivago.booking.service

import com.trivago.booking.api.request.ReservationRequest
import com.trivago.booking.repo.ReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReservationServiceImpl : ReservationService {

    @Autowired
    private lateinit var reservationRepository: ReservationRepository

    @Autowired
    private lateinit var availabilityService: AvailabilityService

    override fun makeRoomBooking(reservationRequest: ReservationRequest): Any {
        val startDate = reservationRequest.startDate
        val endDate = reservationRequest.endDate

        reservationRequest.roomTypes.forEach {roomType ->
            val roomTypeCode = roomType.roomTypeCode
            val occupancy = roomType.occupancy
            val roomTypeAvailable = availabilityService.roomTypeAvailable(startDate, endDate, roomTypeCode, occupancy)

            if (roomTypeAvailable) {
                val customerId = reservationRepository.saveCustomer(reservationRequest.customerFullName, reservationRequest.customerMail)

            }
        }


        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun verifyBooking(reference: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}