package com.trivago.booking.api.controller

import com.trivago.booking.BaseTestController
import com.trivago.booking.model.Room
import com.trivago.booking.model.RoomGuests
import com.trivago.booking.service.AvailabilityService
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.hamcrest.Matchers.`is` as Is
import org.mockito.Mockito.`when` as whenever

@WebMvcTest(AvailabilityController::class, secure = false)
class AvailabilityControllerTest : BaseTestController() {

    @MockBean
    private lateinit var availabilityService: AvailabilityService

    @Test
    fun roomAvailability_whenGet_thenReturn405() {
        mockMvc.perform(get(AvailabilityController.AvailabilityEndpoint))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed)
    }

    @Test
    fun roomAvailability_whenDateInvalid_then400() {
        mockMvc.perform(post(AvailabilityController.AvailabilityEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2019-06-29\",\"endDate\":\"2019-06-31\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun roomAvailability_whenWrongDateFormat_then400() {
        mockMvc.perform(post(AvailabilityController.AvailabilityEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2019-06-29\",\"endDate\":\"2019-06-XX\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun roomAvailability_whenEndDateBeforeStartDate_then400() {
        mockMvc.perform(post(AvailabilityController.AvailabilityEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2019-06-30\",\"endDate\":\"2019-06-29\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun roomAvailability_whenInputProper_thenReturnExpectedResponse() {
        val room = Room(null, "DST", "Double Standard", RoomGuests(2, null, null), 99.0, 4)

        whenever(availabilityService.retrieveAvailableRoomTypes(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyList())).thenReturn(listOf(room))

        mockMvc.perform(post(AvailabilityController.AvailabilityEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"startDate\":\"2019-06-29\",\"endDate\":\"2019-06-30\"," +
                        "\"occupancy\": [{\"adults\": 2, \"juniors\": 0, \"babies\": 0}]}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpectResponse()
                .andDo(generateDocumentation())
    }

    private fun ResultActions.andExpectResponse(): ResultActions {
        this
                .andExpect(MockMvcResultMatchers.jsonPath("startDate", Is("2019-06-29")))
                .andExpect(MockMvcResultMatchers.jsonPath("endDate", Is("2019-06-30")))
                .andExpect(MockMvcResultMatchers.jsonPath("roomTypes[0].roomTypeCode", Is("DST")))
                .andExpect(MockMvcResultMatchers.jsonPath("roomTypes[0].roomTypeName", Is("Double Standard")))
                .andExpect(MockMvcResultMatchers.jsonPath("roomTypes[0].occupancy.adults", Is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("roomTypes[0].amount", Is(99.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("roomTypes[0].roomsAvailable", Is(4)))

        return this
    }

    private fun generateDocumentation(): ResultHandler {
        return document("availability",
                requestFields(
                        PayloadDocumentation
                                .fieldWithPath("startDate")
                                .description("Check-in date of the reservation. Format: YYYY-MM-DD"),
                        PayloadDocumentation
                                .fieldWithPath("endDate")
                                .description("Check-out date of the reservation. Format: YYYY-MM-DD"),
                        PayloadDocumentation
                                .fieldWithPath("occupancy")
                                .description("Array where every element represents a possible occupancy for a room type.").optional(),
                        PayloadDocumentation
                                .fieldWithPath("occupancy[].adults")
                                .description("The number of adults.").optional(),
                        PayloadDocumentation
                                .fieldWithPath("occupancy[].juniors")
                                .description("The number of juniors.").optional(),
                        PayloadDocumentation
                                .fieldWithPath("occupancy[].babies")
                                .description("The number of babies.").optional()
                ),
                responseFields(
                        fieldWithPath("startDate")
                                .description("Check-in date of the reservation."),
                        fieldWithPath("endDate")
                                .description("Check-out date of the reservation."),
                        fieldWithPath("roomTypes")
                                .description("Array where every element represents a possible room type for the requested dates and guests."),
                        fieldWithPath("roomTypes[].roomTypeCode")
                                .description("The three-digit room type code of the room."),
                        fieldWithPath("roomTypes[].roomTypeName")
                                .description("The name of the room type."),
                        fieldWithPath("roomTypes[].occupancy")
                                .description("Represents the possible occupancy for a room type."),
                        fieldWithPath("roomTypes[].occupancy.adults")
                                .description("The number of adults.").optional().type(Int),
                        fieldWithPath("roomTypes[].occupancy.juniors")
                                .description("The number of juniors.").optional().type(Int),
                        fieldWithPath("roomTypes[].occupancy.babies")
                                .description("The number of babies.").optional().type(Int),
                        fieldWithPath("roomTypes[].amount")
                                .description("The amount for a one night stay in the given room type."),
                        fieldWithPath("roomTypes[].roomsAvailable")
                                .description("The number of available rooms of the given room type.")
                )
        )
    }
}