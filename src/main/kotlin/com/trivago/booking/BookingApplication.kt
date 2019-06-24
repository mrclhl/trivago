package com.trivago.booking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookingApplication

fun main(args: Array<String>) {
	runApplication<BookingApplication>(*args)
}
