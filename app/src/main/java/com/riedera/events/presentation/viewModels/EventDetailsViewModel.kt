package com.riedera.events.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riedera.events.domain.AppRepository
import com.riedera.events.domain.models.Event
import com.riedera.events.domain.models.User
import com.riedera.events.presentation.adapters.EventListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class EventDetailsViewModel(private val repository: AppRepository) : ViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event


    fun getEvent(id: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            _event.value = repository.getEventById(id)
        }

    }
}