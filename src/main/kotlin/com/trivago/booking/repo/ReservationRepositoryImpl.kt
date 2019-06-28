package com.trivago.booking.repo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.Connection

@Repository
class ReservationRepositoryImpl : ReservationRepository {

    companion object {
        const val saveCustomerQuery: String = "INSERT INTO guest (customerName, customerMail) VALUES (?, ?);"
    }

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    override fun saveCustomer(customerName: String, customerMail:String): Int {
        val generatedKeyHolder = GeneratedKeyHolder()

        jdbcTemplate.update({ con: Connection ->
            val statement = con.prepareStatement(saveCustomerQuery)
            statement.setString(1, customerName)
            statement.setString(2, customerMail)
            statement
        }, generatedKeyHolder)

        return generatedKeyHolder.key as Int
    }
}