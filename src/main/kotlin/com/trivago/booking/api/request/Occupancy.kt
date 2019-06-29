package com.trivago.booking.api.request

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Occupancy(val adults: Int?, val juniors: Int?, val babies: Int?)