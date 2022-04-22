package com.kareemdev.ticketingapps

data class Seat(
    val id: Int,
    var x: Float? = 0F,
    var y: Float? = 0F,
    val name: String,
    var isBooked: Boolean,
)

