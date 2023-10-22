package com.riedera.events.domain.models

import java.time.LocalDateTime

data class Club (
    val id: Long? = null,
    val name: String,
    val description: String,
    val image: String,
    val events : List<Event>,
    var participants: Int
)