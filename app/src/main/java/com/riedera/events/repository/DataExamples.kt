package com.riedera.events.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.riedera.events.domain.MyDateTimeFormatters
import com.riedera.events.domain.models.Club
import com.riedera.events.domain.models.Event
import java.io.IOException
import java.time.LocalDateTime

object DataExamples {

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
        .create()

    private const val KEY_EVENTS = "MyEvents"
    private const val KEY_CLUBS = "MyClubs"

    fun saveEvents(context: Context, data: List<Event>) {
        val sharedPref = context.getSharedPreferences(KEY_EVENTS, Context.MODE_PRIVATE)
        sharedPref.edit().putString(KEY_EVENTS, gson.toJson(data)).apply()
    }

    fun getEvents(context: Context): List<Event> {
        val sharedPref = context.getSharedPreferences(KEY_EVENTS, Context.MODE_PRIVATE)

        val str = sharedPref.getString(KEY_EVENTS, "")

        val eventListType = object : TypeToken<List<Event>>() {}.type

        val result: List<Event> = gson.fromJson(str, eventListType)

        Log.d("-------------------------------------", result.toString())

        return result
    }


    fun saveClubs(context: Context, data: List<Club>) {
        val sharedPref = context.getSharedPreferences(KEY_CLUBS, Context.MODE_PRIVATE)
        sharedPref.edit().putString(KEY_CLUBS, gson.toJson(data)).apply()
    }

    fun getClubs(context: Context): List<Club> {
        val sharedPref = context.getSharedPreferences(KEY_CLUBS, Context.MODE_PRIVATE)

        val str = sharedPref.getString(KEY_CLUBS, "")

        val clubListType = object : TypeToken<List<Club>>() {}.type

        val result: List<Club> = gson.fromJson(str, clubListType)

        Log.d("-------------------------------------", result.toString())

        return result
    }


    class LocalDateTimeTypeAdapter : TypeAdapter<LocalDateTime>() {
        private val formatter = MyDateTimeFormatters.dateTime

        @Throws(IOException::class)
        override fun read(`in`: com.google.gson.stream.JsonReader?): LocalDateTime? {
            return if (`in`?.peek() == null) {
                `in`?.nextNull()
                null
            } else {
                LocalDateTime.parse(`in`.nextString(), formatter)
            }
        }

        @Throws(IOException::class)
        override fun write(out: com.google.gson.stream.JsonWriter?, value: LocalDateTime?) {
            if (value == null) {
                out?.nullValue()
            } else {
                out?.value(formatter.format(value))
            }
        }
    }
}