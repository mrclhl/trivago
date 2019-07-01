package com.trivago.booking.service

import com.trivago.booking.api.request.RoomType
import com.trivago.booking.exceptions.BookingDoesNotExistException
import com.trivago.booking.exceptions.ReservationNotPossibleException
import com.trivago.booking.model.Booking
import com.trivago.booking.model.Reservation
import com.trivago.booking.model.RoomGuests
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

    override fun makeReservation(startDate: String, endDate: String, customerFullName: String, customerMail: String, roomTypes: List<RoomType>): String {
        val parsedStartDate = LocalDate.parse(startDate)
        val parsedEndDate = LocalDate.parse(endDate)
        val reservations = mutableListOf<Reservation>()

        roomTypes.forEach {roomType ->
            val roomTypeCode = roomType.roomTypeCode
            val occupancy = roomType.occupancy
            val availableRoom = availabilityService.retrieveAvailableRoom(parsedStartDate, parsedEndDate, roomTypeCode, occupancy)

            when (availableRoom) {
                null -> throw ReservationNotPossibleException("One or more room types are not available.")
                else -> reservations.add(Reservation(parsedStartDate, parsedEndDate,
                        occupancy.adults, occupancy.juniors, occupancy.babies,
                        availableRoom.roomId!!))
            }
        }

        if (reservations.isNotEmpty()) {
            val customerId = reservationRepository.saveCustomer(customerFullName, customerMail)
            val reservationReference = createReferenceCode()
            reservationRepository.makeReservation(reservations, customerId, reservationReference)

            return reservationReference
        }

        throw ReservationNotPossibleException("Reservation could not be made for the requested dates and room types.")
    }

    override fun retrieveReservation(reference: String): Booking {
        val booking = reservationRepository.retrieveReservation(reference)

        when (booking) {
            null -> throw BookingDoesNotExistException("There is no booking available for this reference.")
            else -> {
                val totalAmount = booking.rooms.fold(0.0) { total, next -> total + next.amount }
                val days = LocalDate.parse(booking.startDate).until(LocalDate.parse(booking.endDate), ChronoUnit.DAYS)
                booking.total = totalAmount * days
                booking.rooms.forEach { room ->
                    val adults = room.roomGuests.adults
                    val juniors = room.roomGuests.juniors
                    val babies = room.roomGuests.babies
                    room.roomGuests = retrieveAdjustedRoomGuests(adults, juniors, babies)}

                return booking
            }
        }
    }

    fun retrieveAdjustedRoomGuests(adults: Int?, juniors: Int?, babies: Int?): RoomGuests {
        val returnedAdults = if (adults == 0) null else adults
        val returnedJuniors = if (juniors == 0) null else juniors
        val returnedBabies = if (babies == 0) null else babies

        return RoomGuests(returnedAdults, returnedJuniors, returnedBabies)
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