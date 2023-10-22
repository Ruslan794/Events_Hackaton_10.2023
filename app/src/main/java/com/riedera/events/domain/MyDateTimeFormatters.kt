package com.riedera.events.domain

import java.time.format.DateTimeFormatter

object MyDateTimeFormatters {
    val date: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val dateShort: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
    val time: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val dateTime: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    val dateTimeShort: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
    val apiFormatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
}