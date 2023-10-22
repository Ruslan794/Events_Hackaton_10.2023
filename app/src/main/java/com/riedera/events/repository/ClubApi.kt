package com.riedera.events.repository

import com.riedera.events.domain.models.Club

data class ClubApi(
    val clubName: String, val description: String, val id: Long, val image: String
)

fun ClubApi.toClub(): Club {
  return  Club(
        id = id,
        name = clubName,
        description = description,
        image = image,
        events = emptyList(),
        participants = when (id){
            1L -> 10;
            2L-> 13;
            3L -> 6;
            4L->9;
            5L->21;
            else -> {4}
        }

    )
}