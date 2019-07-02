package com.trivago.booking.service

import com.trivago.booking.api.request.RoomType
import com.trivago.booking.exceptions.BookingDoesNotExistException
import com.trivago.booking.exceptions.ReservationNotPossibleException
import com.trivago.booking.model.Booking
import com.trivago.booking.model.Room
import com.trivago.booking.model.RoomGuests
import com.trivago.booking.repo.ReservationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDate
import org.mockito.Mockito.`when` as whenever

@RunWith(MockitoJUnitRunner::class)
class ReservationServiceImplTest {

    @InjectMocks
    private lateinit var reservationServiceImpl: ReservationServiceImpl

    @Mock
    private lateinit var reservationRepository: ReservationRepository

    @Mock
    private lateinit var availabilityService: AvailabilityService

    @Test(expected = ReservationNotPossibleException::class)
    fun makeReservation_whenRoomTypesEmpty_thenThrowException() {
        reservationServiceImpl.makeReservation("2019-06-25","2019-06-30", "Marcel Heil", "marcel@heil.com", emptyList())
    }

    @Test(expected = ReservationNotPossibleException::class)
    fun makeReservation_whenOneRequestedRoomTypeNotAvailable_thenThrowException() {
        val requestedRoomGuests = RoomGuests(1, null, null)
        val roomType = RoomType()
        roomType.roomTypeCode = "DST"
        roomType.occupancy = requestedRoomGuests

        whenever(availabilityService.retrieveAvailableRoom(LocalDate.parse("2019-06-25"), LocalDate.parse("2019-06-30"), "DST", requestedRoomGuests)).thenReturn(null)

        reservationServiceImpl.makeReservation("2019-06-25","2019-06-30", "Marcel Heil", "marcel@heil.com", listOf(roomType))
    }

    @Test
    fun makeReservation_whenRequestedRoomTypeAvailable_thenReturnReservationWithReference() {
        val room = Room(1, "DST", "Double Standard", RoomGuests(2, 0, 0), 99.0, 4)
        val requestedRoomGuests = RoomGuests(1, null, null)
        val roomType = RoomType()
        roomType.roomTypeCode = "DST"
        roomType.occupancy = requestedRoomGuests

        whenever(availabilityService.retrieveAvailableRoom(LocalDate.parse("2019-06-25"), LocalDate.parse("2019-06-30"), "DST", requestedRoomGuests)).thenReturn(room)
        whenever(reservationRepository.saveCustomer("Marcel Heil", "marcel@heil.com")).thenReturn(1)

        val reservation = reservationServiceImpl.makeReservation("2019-06-25", "2019-06-30", "Marcel Heil", "marcel@heil.com", listOf(roomType))

        verify(reservationRepository).makeReservation(ArgumentMatchers.anyList(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyString())

        assertThat(reservation).isNotBlank()
        assertThat(reservation.length).isEqualTo(6)
    }

    @Test(expected = BookingDoesNotExistException::class)
    fun retrieveReservation_whenReservationNotAvailable_thenThrowException() {
        whenever(reservationRepository.retrieveReservation(ArgumentMatchers.anyString())).thenReturn(null)

        reservationServiceImpl.retrieveReservation("A1B2C3")
    }

    @Test
    fun retrieveReservation_whenReservationAvailable_thenReturnBooking() {
        val room1 = Room(null, "DST", "Double Standard", RoomGuests(2, 0, 0), 99.0, null)
        val room2 = Room(null, "DSP", "Double Superior", RoomGuests(2, 0, 1), 119.0, null)
        val room3 = Room(null, "APT", "Apartment", RoomGuests(2, 2, 1), 169.0, null)
        val booking = Booking("2019-06-25", "2019-06-30", 0.0, "Marcel Heil", "marcel@heil.com", listOf(room1, room2, room3))
        val expectedTotal = (room1.amount + room2.amount + room3.amount) * 5

        whenever(reservationRepository.retrieveReservation(ArgumentMatchers.anyString())).thenReturn(booking)

        val reservation = reservationServiceImpl.retrieveReservation("A1B2C3")

        assertThat(reservation.customerName).isEqualTo("Marcel Heil")
        assertThat(reservation.customerMail).isEqualTo("marcel@heil.com")
        assertThat(reservation.startDate).isEqualTo("2019-06-25")
        assertThat(reservation.endDate).isEqualTo("2019-06-30")
        assertThat(reservation.total).isEqualTo(expectedTotal)
        assertThat(reservation.rooms).hasSize(3)
        // Room 1
        assertThat(reservation.rooms[0].roomTypeCode).isEqualTo("DST")
        assertThat(reservation.rooms[0].roomGuests.adults).isEqualTo(2)
        assertThat(reservation.rooms[0].roomGuests.juniors).isNull()
        assertThat(reservation.rooms[0].roomGuests.babies).isNull()
        // Room 2
        assertThat(reservation.rooms[1].roomTypeCode).isEqualTo("DSP")
        assertThat(reservation.rooms[1].roomGuests.adults).isEqualTo(2)
        assertThat(reservation.rooms[1].roomGuests.juniors).isNull()
        assertThat(reservation.rooms[1].roomGuests.babies).isEqualTo(1)
        // Room 3
        assertThat(reservation.rooms[2].roomTypeCode).isEqualTo("APT")
        assertThat(reservation.rooms[2].roomGuests.adults).isEqualTo(2)
        assertThat(reservation.rooms[2].roomGuests.juniors).isEqualTo(2)
        assertThat(reservation.rooms[2].roomGuests.babies).isEqualTo(1)
    }
}