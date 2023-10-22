package com.riedera.events.presentation.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.riedera.events.domain.AppRepository
import com.riedera.events.domain.models.Club
import com.riedera.events.domain.models.Event
import com.riedera.events.domain.models.User
import com.riedera.events.presentation.adapters.ClubListAdapter
import com.riedera.events.presentation.adapters.EventListAdapter
import com.riedera.events.repository.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDateTime


class HomeViewModel(private val repository: AppRepository) : ViewModel() {

    private val _eventsList = MutableLiveData<List<Event>>()
    val eventsList: LiveData<List<Event>> = _eventsList
    var eventsAdapter: EventListAdapter = EventListAdapter(mutableListOf())

    private val _clubsList = MutableLiveData<List<Club>>()
    val clubsList: LiveData<List<Club>> = _clubsList
    var clubsAdapter: ClubListAdapter = ClubListAdapter(mutableListOf())

    init {


        viewModelScope.launch(Dispatchers.Main) {
            _eventsList.value = repository.getAllEvents()
            _clubsList.value = repository.getAllClubs()
        }

    }

}
