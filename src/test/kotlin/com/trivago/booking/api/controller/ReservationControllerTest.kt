package com.trivago.booking.api.controller

import com.trivago.booking.BaseTestController
import com.trivago.booking.api.controller.ReservationController.Companion.ReservationEndpoint
import com.trivago.booking.api.controller.ReservationController.Companion.VerificationEndpoint
import com.trivago.booking.model.Booking
import com.trivago.booking.model.Room
import com.trivago.booking.model.RoomGuests
import com.trivago.booking.service.ReservationService
import org.hamcrest.Matchers
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.hamcrest.Matchers.`is` as Is
import org.mockito.Mockito.`when` as whenever

@WebMvcTest(ReservationController::class)
class ReservationControllerTest : BaseTestController() {

    @MockBean
    private lateinit var reservationService: ReservationService

    @Test
    fun reservation_whenGet_thenReturn405() {
        mockMvc.perform(MockMvcRequestBuilders.get(ReservationEndpoint))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed)
    }

    @Test
    fun reservation_whenDateInvalid_then400() {
        mockMvc.perform(MockMvcRequestBuilders.post(ReservationEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2019-06-25\",\"endDate\":\"2019-06-31\", " +
                        "\"customerFullName\": \"Marcel Heil\", \"customerMail\": \"marcel@heil.com\", " +
                        "\"roomTypes\": [{\"roomTypeCode\": \"DST\", \"occupancy\": {\"adults\": 2, \"juniors\": 0, \"babies\": 0}}]}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun reservation_whenWrongDateFormat_then400() {
        mockMvc.perform(MockMvcRequestBuilders.post(ReservationEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2019-06-25\",\"endDate\":\"2019-30-06\", " +
                        "\"customerFullName\": \"Marcel Heil\", \"customerMail\": \"marcel@heil.com\", " +
                        "\"roomTypes\": [{\"roomTypeCode\": \"DST\", \"occupancy\": {\"adults\": 2, \"juniors\": 0, \"babies\": 0}}]}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun reservation_whenEndDateBeforeStartDate_then400() {
        mockMvc.perform(MockMvcRequestBuilders.post(ReservationEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2019-06-25\",\"endDate\":\"2019-06-24\", " +
                        "\"customerFullName\": \"Marcel Heil\", \"customerMail\": \"marcel@heil.com\", " +
                        "\"roomTypes\": [{\"roomTypeCode\": \"DST\", \"occupancy\": {\"adults\": 2, \"juniors\": 0, \"babies\": 0}}]}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun reservation_whenNoNameEntered_then400() {
        mockMvc.perform(MockMvcRequestBuilders.post(ReservationEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2019-06-25\",\"endDate\":\"2019-06-24\", " +
                        "\"customerFullName\": \"\", \"customerMail\": \"marcel@heil.com\", " +
                        "\"roomTypes\": [{\"roomTypeCode\": \"DST\", \"occupancy\": {\"adults\": 2, \"juniors\": 0, \"babies\": 0}}]}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun reservation_whenWrongMailFormat_then400() {
        mockMvc.perform(MockMvcRequestBuilders.post(ReservationEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2019-06-25\",\"endDate\":\"2019-06-24\", " +
                        "\"customerFullName\": \"\", \"customerMail\": \"marcel@heil\", " +
                        "\"roomTypes\": [{\"roomTypeCode\": \"DST\", \"occupancy\": {\"adults\": 2, \"juniors\": 0, \"babies\": 0}}]}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun reservation_whenInputProper_thenReturnExpectedResponse() {
        val room = Room(null, "DST", "Double Standard", RoomGuests(2, null, null), 99.0, null)
        val booking = Booking("2019-06-25", "2019-06-30", 495.0, "Marcel Heil", "marcel@heil.com", listOf(room))

        whenever(reservationService.makeReservation(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyList())).thenReturn("A1B2C3")
        whenever(reservationService.retrieveReservation(ArgumentMatchers.anyString())).thenReturn(booking)

        mockMvc.perform(MockMvcRequestBuilders.post(ReservationEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2019-06-25\",\"endDate\":\"2019-06-30\", " +
                        "\"customerFullName\": \"Marcel Heil\", \"customerMail\": \"marcel@heil.com\", " +
                        "\"roomTypes\": [{\"roomTypeCode\": \"DST\", \"occupancy\": {\"adults\": 2, \"juniors\": 0, \"babies\": 0}}]}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpectResponse()
                .andDo(generateDocumentationForReservationEndpoint())
    }

    @Test
    fun verification_whenGet_thenReturn405() {
        mockMvc.perform(MockMvcRequestBuilders.get(VerificationEndpoint))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed)
    }

    @Test
    fun verification_whenInvalidReference_thenReturn400() {
        mockMvc.perform(MockMvcRequestBuilders.post(VerificationEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"reference\": \"\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun verification_whenValid_Reference_thenReturnExpectedResponse() {
        val room = Room(null, "DST", "Double Standard", RoomGuests(2, null, null), 99.0, null)
        val booking = Booking("2019-06-25", "2019-06-30", 495.0, "Marcel Heil", "marcel@heil.com", listOf(room))

        whenever(reservationService.retrieveReservation(ArgumentMatchers.anyString())).thenReturn(booking)

        mockMvc.perform(MockMvcRequestBuilders.post(VerificationEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"reference\": \"A1B2C3\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpectResponse()
                .andDo(generateDocumentationForVerificationEndpoint())
    }

    private fun ResultActions.andExpectResponse(): ResultActions {
        this
                .andExpect(MockMvcResultMatchers.jsonPath("startDate", Matchers.`is`("2019-06-25")))
                .andExpect(MockMvcResultMatchers.jsonPath("endDate", Matchers.`is`("2019-06-30")))
                .andExpect(MockMvcResultMatchers.jsonPath("reference", Matchers.`is`("A1B2C3")))
                .andExpect(MockMvcResultMatchers.jsonPath("totalAmount", Matchers.`is`(495.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("customerFullName", Matchers.`is`("Marcel Heil")))
                .andExpect(MockMvcResultMatchers.jsonPath("customerMail", Matchers.`is`("marcel@heil.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("roomTypes[0].roomTypeCode", Matchers.`is`("DST")))
                .andExpect(MockMvcResultMatchers.jsonPath("roomTypes[0].roomTypeName", Matchers.`is`("Double Standard")))
                .andExpect(MockMvcResultMatchers.jsonPath("roomTypes[0].occupancy.adults", Matchers.`is`(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("roomTypes[0].amount", Matchers.`is`(99.0)))

        return this
    }

    private fun generateDocumentationForReservationEndpoint(): ResultHandler {
        return MockMvcRestDocumentation.document("reservation",
                PayloadDocumentation.requestFields(
                        PayloadDocumentation
                                .fieldWithPath("startDate")
                                .description("Check-in date of the reservation. Format: YYYY-MM-DD"),
                        PayloadDocumentation
                                .fieldWithPath("endDate")
                                .description("Check-out date of the reservation. Format: YYYY-MM-DD"),
                        PayloadDocumentation
                                .fieldWithPath("customerFullName")
                                .description("The full name of the customer."),
                        PayloadDocumentation
                                .fieldWithPath("customerMail")
                                .description("The mail address of the customer."),
                        PayloadDocumentation
                                .fieldWithPath("roomTypes")
                                .description("Array where every element represents a requested room type."),
                        PayloadDocumentation
                                .fieldWithPath("roomTypes[].roomTypeCode")
                                .description("The three-digit room type code of the requested room type."),
                        PayloadDocumentation
                                .fieldWithPath("roomTypes[]occupancy")
                                .description("The requested occupancy for the room type."),
                        PayloadDocumentation
                                .fieldWithPath("roomTypes[].occupancy.adults")
                                .description("The number of adult guests.").optional(),
                        PayloadDocumentation
                                .fieldWithPath("roomTypes[].occupancy.juniors")
                                .description("The number of junior guests.").optional(),
                        PayloadDocumentation
                                .fieldWithPath("roomTypes[].occupancy.babies")
                                .description("The number of baby guests.").optional()
                ),
                responseFields()
        )
    }

    private fun generateDocumentationForVerificationEndpoint(): ResultHandler {
        return MockMvcRestDocumentation.document("reservation",
                PayloadDocumentation.requestFields(
                        PayloadDocumentation
                                .fieldWithPath("reference")
                                .description("The reservation reference to verify.")
                        ),
                responseFields()
        )
    }

    private fun responseFields(): ResponseFieldsSnippet? {
        return PayloadDocumentation.responseFields(
                PayloadDocumentation.fieldWithPath("startDate")
                        .description("Check-in date of the reservation."),
                PayloadDocumentation.fieldWithPath("endDate")
                        .description("Check-out date of the reservation."),
                PayloadDocumentation
                        .fieldWithPath("reference")
                        .description("The reservation reference."),
                PayloadDocumentation
                        .fieldWithPath("customerMail")
                        .description("The mail address of the customer."),
                PayloadDocumentation
                        .fieldWithPath("customerFullName")
                        .description("The full name of the customer."),
                PayloadDocumentation
                        .fieldWithPath("customerMail")
                        .description("The mail address of the customer."),
                PayloadDocumentation.fieldWithPath("roomTypes")
                        .description("Array where every element represents a reserved room type for the requested dates and guests."),
                PayloadDocumentation.fieldWithPath("totalAmount")
                        .description("The total amount of the reservation"),
                PayloadDocumentation.fieldWithPath("roomTypes[].roomTypeCode")
                        .description("The three-digit room type code of the room."),
                PayloadDocumentation.fieldWithPath("roomTypes[].roomTypeName")
                        .description("The name of the room type."),
                PayloadDocumentation.fieldWithPath("roomTypes[].occupancy")
                        .description("Represents the requested occupancy for a room type."),
                PayloadDocumentation.fieldWithPath("roomTypes[].occupancy.adults")
                        .description("The number of adults.").optional().type(Int),
                PayloadDocumentation.fieldWithPath("roomTypes[].occupancy.juniors")
                        .description("The number of juniors.").optional().type(Int),
                PayloadDocumentation.fieldWithPath("roomTypes[].occupancy.babies")
                        .description("The number of babies.").optional().type(Int),
                PayloadDocumentation.fieldWithPath("roomTypes[].amount")
                        .description("The amount for a one night stay in the given room type.")
        )
    }
}