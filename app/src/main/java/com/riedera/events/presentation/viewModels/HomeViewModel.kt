package com.riedera.events.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riedera.events.domain.AppRepository
import com.riedera.events.domain.models.Club
import com.riedera.events.domain.models.Event
import com.riedera.events.presentation.adapters.ClubListAdapter
import com.riedera.events.presentation.adapters.EventListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel(private val repository: AppRepository) : ViewModel() {

    private val _eventsList = MutableLiveData<List<Event>>()
    val eventsList: LiveData<List<Event>> = _eventsList
    var eventsAdapter: EventListAdapter = EventListAdapter(mutableListOf())

    private val _clubsList = MutableLiveData<List<Club>>()
    val clubsList: LiveData<List<Club>> = _clubsList
    var clubsAdapter: ClubListAdapter = ClubListAdapter(mutableListOf())

    init {
        viewModelScope.launch(Dispatchers.Main) {

            val events = repository.getAllEvents()
            val clubs = repository.getAllClubs()

            _eventsList.value = events
            _clubsList.value = clubs
        }

    }

}
