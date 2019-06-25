package com.trivago.booking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan("com.trivago.booking.api")
@SpringBootApplication
class BookingApplication

fun main(args: Array<String>) {
	runApplication<BookingApplication>(*args)
}
