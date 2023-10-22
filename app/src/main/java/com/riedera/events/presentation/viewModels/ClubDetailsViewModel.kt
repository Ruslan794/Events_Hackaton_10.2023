package com.riedera.events.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riedera.events.domain.AppRepository
import com.riedera.events.domain.models.Club
import com.riedera.events.domain.models.Event
import com.riedera.events.domain.models.User
import com.riedera.events.presentation.adapters.ClubListAdapter
import com.riedera.events.presentation.adapters.EventListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ClubDetailsViewModel(private val repository: AppRepository) : ViewModel() {

    private val _club = MutableLiveData<Club>()
    val club: LiveData<Club> = _club

    private val _eventsList = MutableLiveData<List<Event>>()
    val eventsList: LiveData<List<Event>> = _eventsList
    var eventsAdapter: EventListAdapter = EventListAdapter(mutableListOf())

    fun getClub(id: Long){
        viewModelScope.launch(Dispatchers.Main) {
            _club.value = repository.getClubById(id)
            _eventsList.value = repository.getClubEventsByClubId(id)
        }
    }
}