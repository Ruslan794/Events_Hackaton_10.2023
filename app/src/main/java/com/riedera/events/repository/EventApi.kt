package com.riedera.events.repository

import com.riedera.events.domain.MyDateTimeFormatters
import com.riedera.events.domain.models.Event
import java.time.LocalDateTime

data class EventApi(
    val date: String,
    val description: String,
    val eventName: String,
    val id: Long,
    val image: String,
    val nameOfClubOrganizer: String?,
    val nameOfUserOrganizer: String?,
    val place: String,
    val quantityOfParticipants: Int,
    val verified: Boolean
)

fun EventApi.toEvent(): Event {
return Event(
    id = id,
    name =eventName,
    dateAndTime = LocalDateTime.parse(date, MyDateTimeFormatters.apiFormatter),
    description = description,
    image = image,
    verified = verified,
    club = null,
    participants = quantityOfParticipants,
    creator = nameOfUserOrganizer ?: nameOfClubOrganizer!!,
    location = place
)
}