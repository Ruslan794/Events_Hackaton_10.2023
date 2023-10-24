package com.riedera.events.domain.models

import java.time.LocalDateTime

data class Event (
    val id: Long ,
    val name: String,
    val dateAndTime : LocalDateTime?,
    val description: String,
    val image: String,
    val verified: Boolean,
    val club : Long? = null,
    var participants:Int,
    val creator: String,
    val location : String
)