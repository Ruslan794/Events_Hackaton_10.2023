package com.riedera.events.repository

import android.util.Log
import com.riedera.events.domain.AppRepository
import com.riedera.events.domain.models.Club
import com.riedera.events.domain.models.Event
import com.riedera.events.domain.models.NewEvent
import com.riedera.events.domain.models.User

class AppRepositoryImpl(private val api: ApiInterface) : AppRepository {


    override suspend fun getAllEvents(): List<Event> {
        val result = mutableListOf<Event>()
        try {
            api.getAllEvents().forEach {

                val item = it.toEvent()
                result.add(item)
                Log.d("--------------------", it.toEvent().toString())
            }
        } catch (e: Exception) {

        }
        return result.toList()
    }

    override suspend fun getAllClubs(): List<Club> {
        val result = mutableListOf<Club>()
        try {
            api.getAllClubs().forEach {
                val item = it.toClub()
                result.add(item)
                Log.d("--------------------", it.toClub().toString())
            }
        } catch (e: Exception) {

        }
        return result.toList()
    }

    override suspend fun getEventsByUser(userToken: String): List<Event> {
        val result = mutableListOf<Event>()
        try {
            api.getParticipantsOfEvent(userToken).forEach {
                if (it is EventApi) {
                    result.add(it.toEvent())
                    Log.d("--------------------", it.toEvent().toString())
                }
            }
        } catch (e: Exception) {

        }
        return result.toList()
    }


    override suspend fun getEventById(id: Long): Event {
        val item = api.getEventById(id).toEvent()
        return item
    }

    override suspend fun getClubById(id: Long): Club {
        val item = api.getClubById(id).toClub()
        return item
    }

    override suspend fun getClubEventsByClubId(id: Long): List<Event> {
        val result = mutableListOf<Event>()
        try {
            api.getClubEventsByClubId(id).forEach {
                if (it is EventApi) {
                    val item = it.toEvent()
                    result.add(item)
                    Log.d("--------------------", it.toEvent().toString())
                }
            }
        } catch (e: Exception) {

        }
        return result.toList()
    }

    override suspend fun getUserById(id: Long): User {
        TODO("Not yet implemented")
    }


    override suspend fun getClubsByUser(userToken: String): List<Club> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadNewEvent(event: NewEvent) {

    }


}

