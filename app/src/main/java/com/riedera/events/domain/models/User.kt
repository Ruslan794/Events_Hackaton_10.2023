package com.riedera.events.domain.models

data class User(
    val id: Long,
    val login: String,
    val password: String,
    val clubs: List<Club>,
    val token: String,
    val subscribedEvents: List<Event>
)