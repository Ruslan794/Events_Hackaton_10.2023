package com.riedera.events.domain.models

import java.time.LocalDateTime

data class NewEvent (
    val name: String,
    val dateAndTime : LocalDateTime,
    val description: String,
)