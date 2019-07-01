package com.trivago.booking.service

import com.trivago.booking.model.Room
import com.trivago.booking.model.RoomGuests
import com.trivago.booking.repo.AvailabilityRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDate
import org.hamcrest.Matchers.`is` as Is
import org.mockito.Mockito.`when` as whenever

@RunWith(MockitoJUnitRunner::class)
class AvailabilityServiceImplTest {

    @InjectMocks
    private lateinit var availabilityServiceImpl: AvailabilityServiceImpl

    @Mock
    private lateinit var availabilityRepository: AvailabilityRepository

    @Test
    internal fun retrieveAvailableRoomTypes_whenRoomGuestsNull_thenReturnAllAvailableRoomOptions() {
        val room1 = Room(null, "DST", "Double Standard", RoomGuests(2, 0, 0), 99.0, 4)
        val room2 = Room(null, "FML", "Family Large", RoomGuests(2, 2, 1), 169.0, 2)

        whenever(availabilityRepository.retrieveAvailableRoomTypes(LocalDate.parse("2019-06-25"), LocalDate.parse("2019-06-30"))).thenReturn(listOf(room1, room2))

        val availableRoomTypes = availabilityServiceImpl.retrieveAvailableRoomTypes("2019-06-25", "2019-06-30", null)

        assertThat(availableRoomTypes.size).isEqualTo(2)
        // Room 1
        assertThat(availableRoomTypes[0].roomTypeCode).isEqualTo("DST")
        assertThat(availableRoomTypes[0].roomTypeName).isEqualTo("Double Standard")
        assertThat(availableRoomTypes[0].roomGuests.adults).isEqualTo(2)
        assertThat(availableRoomTypes[0].roomGuests.juniors).isEqualTo(0)
        assertThat(availableRoomTypes[0].roomGuests.babies).isEqualTo(0)
        assertThat(availableRoomTypes[0].amount).isEqualTo(99.0)
        assertThat(availableRoomTypes[0].roomsAvailable).isEqualTo(4)
        // Room 2
        assertThat(availableRoomTypes[1].roomTypeCode).isEqualTo("FML")
        assertThat(availableRoomTypes[1].roomTypeName).isEqualTo("Family Large")
        assertThat(availableRoomTypes[1].roomGuests.adults).isEqualTo(2)
        assertThat(availableRoomTypes[1].roomGuests.juniors).isEqualTo(2)
        assertThat(availableRoomTypes[1].roomGuests.babies).isEqualTo(1)
        assertThat(availableRoomTypes[1].amount).isEqualTo(169.0)
        assertThat(availableRoomTypes[1].roomsAvailable).isEqualTo(2)
    }

    @Test
    internal fun retrieveAvailableRoomTypes_whenRoomGuestsDefined_thenReturnSpecificRoomOptions() {
        val room1 = Room(null, "DST", "Double Standard", RoomGuests(2, 0, 0), 99.0, 4)
        val room2 = Room(null, "DSP", "Double Superior", RoomGuests(2, 0, 1), 119.0, 3)
        val room3 = Room(null, "APT", "Apartment", RoomGuests(2, 2, 1), 169.0, 2)
        val requestedGuestsRoom1 = RoomGuests(1, null, null)
        val requestedGuestsRoom2 = RoomGuests(2, 1, 1)

        whenever(availabilityRepository.retrieveAvailableRoomTypesByOccupancy(LocalDate.parse("2019-06-25"), LocalDate.parse("2019-06-30"), requestedGuestsRoom1)).thenReturn(listOf(room1, room2))
        whenever(availabilityRepository.retrieveAvailableRoomTypesByOccupancy(LocalDate.parse("2019-06-25"), LocalDate.parse("2019-06-30"), requestedGuestsRoom2)).thenReturn(listOf(room3))

        val availableRoomTypes = availabilityServiceImpl.retrieveAvailableRoomTypes("2019-06-25", "2019-06-30", listOf(requestedGuestsRoom1, requestedGuestsRoom2))

        assertThat(availableRoomTypes.size).isEqualTo(3)
        // Room 1
        assertThat(availableRoomTypes[0].roomTypeCode).isEqualTo("DST")
        assertThat(availableRoomTypes[0].roomTypeName).isEqualTo("Double Standard")
        assertThat(availableRoomTypes[0].roomGuests.adults).isEqualTo(1)
        assertThat(availableRoomTypes[0].roomGuests.juniors).isNull()
        assertThat(availableRoomTypes[0].roomGuests.babies).isNull()
        assertThat(availableRoomTypes[0].amount).isEqualTo(99.0)
        // Room 2
        assertThat(availableRoomTypes[1].roomTypeCode).isEqualTo("DSP")
        assertThat(availableRoomTypes[1].roomTypeName).isEqualTo("Double Superior")
        assertThat(availableRoomTypes[1].roomGuests.adults).isEqualTo(1)
        assertThat(availableRoomTypes[1].roomGuests.juniors).isNull()
        assertThat(availableRoomTypes[1].roomGuests.babies).isNull()
        assertThat(availableRoomTypes[1].amount).isEqualTo(119.0)
        // Room 3
        assertThat(availableRoomTypes[2].roomTypeCode).isEqualTo("APT")
        assertThat(availableRoomTypes[2].roomTypeName).isEqualTo("Apartment")
        assertThat(availableRoomTypes[2].roomGuests.adults).isEqualTo(2)
        assertThat(availableRoomTypes[2].roomGuests.juniors).isEqualTo(1)
        assertThat(availableRoomTypes[2].roomGuests.babies).isEqualTo(1)
        assertThat(availableRoomTypes[2].amount).isEqualTo(169.0)
    }
}