package com.riedera.events.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface ApiInterface  {


    @GET("event/all")
    suspend fun getAllEvents(): List<EventApi>

    @GET("event/{id}")
    suspend fun getEventById(@Path("id") id: Long): EventApi

    @GET("club/all")
    suspend fun getAllClubs(): List<ClubApi>

    @GET("club/{id}")
    suspend fun getClubById(@Path("id") id: Long): ClubApi

    @GET("club/{id}/events")
    suspend fun getClubEventsByClubId(@Path("id") id: Long): List<EventApi>

    @GET("all-my-participated-events")
    suspend fun getParticipantsOfEvent(
        @Header("Authorization") token: String
    ) : List<EventApi>

    @GET("event/{id}/participants-count")
    suspend fun getParticipantsOfEvent(
        @Path("id") id: Long
    ) : Int


    companion object {
        fun create() : ApiInterface {

            val client = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://events-production-6139.up.railway.app/")
                .build().create(ApiInterface::class.java)
        }
    }
}