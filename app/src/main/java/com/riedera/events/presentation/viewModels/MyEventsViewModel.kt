package com.riedera.events.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riedera.events.domain.AppRepository
import com.riedera.events.domain.models.Event
import com.riedera.events.presentation.adapters.EventListAdapter
import com.riedera.events.presentation.fragments.EventDetailsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyEventsViewModel(private val repository: AppRepository) : ViewModel() {

    private val _eventsList = MutableLiveData<List<Event>>()
    val eventsList: LiveData<List<Event>> = _eventsList
    var eventsAdapter: EventListAdapter = EventListAdapter(mutableListOf())

    init {

        viewModelScope.launch(Dispatchers.Main) {
            val list = repository.getAllEvents()
            if (EventDetailsFragment.savedEventId != null) _eventsList.value =
                list.subList(0, 1) + list.subList(3, 4) + repository.getEventById(
                    EventDetailsFragment.savedEventId!!
                )
            else _eventsList.value = list.subList(0, 1) + list.subList(3, 4)
        }
    }

    companion object {
        private const val TOKEN =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpYnVkYXljaGlldmFAZ21haWwuY29tIiwiaWF0IjoxNjk3ODUyMjA2LCJleHAiOjE2OTc4OTg2MDZ9.uWViMLK-l4fy2kLuGynl9gDN4PSeTEv6PM_mw3PCsG8RRx-wVFUPFnkNbGz3ctSNDg191AK87_nwLYgbS16IWA"
    }

}