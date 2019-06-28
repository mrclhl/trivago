package com.trivago.booking.service

import com.trivago.booking.api.request.ReservationRequest
import com.trivago.booking.model.Reservation
import com.trivago.booking.model.ReservationReference
import com.trivago.booking.repo.ReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Random

@Service
class ReservationServiceImpl : ReservationService {

    private val charPool : List<Char> = ('A'..'Z') + ('0'..'9')

    @Autowired
    private lateinit var reservationRepository: ReservationRepository

    @Autowired
    private lateinit var availabilityService: AvailabilityService

    override fun makeReservations(reservationRequest: ReservationRequest): ReservationReference? {
        val startDate = LocalDate.parse(reservationRequest.startDate)
        val endDate = LocalDate.parse(reservationRequest.endDate)
        val reservations = mutableListOf<Reservation>()

        reservationRequest.roomTypes.forEach {roomType ->
            val roomTypeCode = roomType.roomTypeCode
            val occupancy = roomType.occupancy
            val availableRoom = availabilityService.retrieveAvailableRoom(startDate, endDate, roomTypeCode, occupancy)

            if (availableRoom != null) {
                reservations.add(Reservation(startDate, endDate, availableRoom.roomId!!))
            }
        }

        if (reservations.isNotEmpty()) {
            val customerId = reservationRepository.saveCustomer(reservationRequest.customerFullName, reservationRequest.customerMail)
            val reservationReference = createBookingReference()
            reservationRepository.makeReservations(reservations, customerId, reservationReference)

            return reservationReference
        }

        return null
    }

    override fun verifyBooking(reservationReference: ReservationReference): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun createBookingReference(): ReservationReference {
        val randomString = (1..6).map { Random().nextInt(charPool.size) }.map(charPool::get).joinToString("")
//        val isReferenceExisting = reservationRepository.referenceExisting(randomString)

//        if (isReferenceExisting) {
//            createBookingReference()
//        }

        return ReservationReference(randomString)
    }
}