package com.riedera.events.domain

import com.riedera.events.domain.models.Club
import com.riedera.events.domain.models.Event
import com.riedera.events.domain.models.NewEvent
import com.riedera.events.domain.models.User
import com.riedera.events.domain.models.old.InfoItem

interface AppRepository {

    suspend fun getAllEvents(): List<Event>
    suspend fun getAllClubs(): List<Club>
    // suspend fun getAllUsers(): List<User>

    suspend fun getEventById(id: Long): Event
    suspend fun getClubById(id: Long): Club
    suspend fun getUserById(id: Long): User

    suspend fun getClubEventsByClubId(id: Long): List<Event>

    suspend fun getEventsByUser(userToken: String): List<Event>
    suspend fun getClubsByUser(userToken: String): List<Club>

 //  suspend fun getListOfEventsByIds(ids: List<Long>): List<Event>
 //  suspend fun getListOfClubsByIds(ids: List<Long>): List<Club>
 //  suspend fun getListOfUsersByIds(ids: List<Long>): List<User>

    suspend fun uploadNewEvent(event: NewEvent)

    // suspend fun uploadNewClub(club:Club)
}