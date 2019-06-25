package com.trivago.booking.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController {

    @RequestMapping("/hello", method = [(RequestMethod.GET)])
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): String {
        return "Hello, $name!"
    }
}