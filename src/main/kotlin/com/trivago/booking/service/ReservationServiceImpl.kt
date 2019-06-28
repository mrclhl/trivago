package com.trivago.booking.service

import com.trivago.booking.api.exceptions.BookingDoesNotExistException
import com.trivago.booking.api.exceptions.ReservationNotPossibleException
import com.trivago.booking.api.request.ReservationRequest
import com.trivago.booking.model.Booking
import com.trivago.booking.model.Reservation
import com.trivago.booking.repo.ReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Random

@Service
class ReservationServiceImpl : ReservationService {

    private val charPool : List<Char> = ('A'..'Z') + ('0'..'9')

    @Autowired
    private lateinit var reservationRepository: ReservationRepository

    @Autowired
    private lateinit var availabilityService: AvailabilityService

    override fun makeReservation(reservationRequest: ReservationRequest): String {
        val startDate = LocalDate.parse(reservationRequest.startDate)
        val endDate = LocalDate.parse(reservationRequest.endDate)
        val reservations = mutableListOf<Reservation>()

        reservationRequest.roomTypes.forEach {roomType ->
            val roomTypeCode = roomType.roomTypeCode
            val occupancy = roomType.occupancy
            val availableRoom = availabilityService.retrieveAvailableRoom(startDate, endDate, roomTypeCode, occupancy)

            if (availableRoom != null) {
                reservations.add(Reservation(startDate, endDate,
                        occupancy.adults, occupancy.juniors, occupancy.babies,
                        availableRoom.roomId!!))
            }
        }

        if (reservations.isNotEmpty()) {
            val customerId = reservationRepository.saveCustomer(reservationRequest.customerFullName, reservationRequest.customerMail)
            val reservationReference = createReferenceCode()
            reservationRepository.makeReservation(reservations, customerId, reservationReference)

            return reservationReference
        }

        throw ReservationNotPossibleException("Reservation could not be made for the requested dates and room types.")
    }

    override fun retrieveBooking(reference: String): Booking {
        val booking = reservationRepository.retrieveBooking(reference)

        when (booking) {
            null -> throw BookingDoesNotExistException("There is no booking available for this reference.")
            else -> {
                val totalAmount = booking.rooms.fold(0.0) { total, next -> total + next.amount }
                val days = LocalDate.parse(booking.startDate).until(LocalDate.parse(booking.endDate), ChronoUnit.DAYS)
                booking.total = totalAmount * days
                return booking
            }
        }
    }

    fun createReferenceCode(): String {
        val reference = (1..6).map { Random().nextInt(charPool.size) }.map(charPool::get).joinToString("")
        val referenceAlreadyExisting = reservationRepository.referenceAlreadyExisting(reference)

        if (referenceAlreadyExisting) {
            createReferenceCode()
        }

        return reference
    }
}