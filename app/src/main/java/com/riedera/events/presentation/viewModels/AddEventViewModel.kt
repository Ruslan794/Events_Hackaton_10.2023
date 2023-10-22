package com.riedera.events.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riedera.events.domain.AppRepository
import com.riedera.events.domain.models.Event
import com.riedera.events.domain.models.NewEvent
import com.riedera.events.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AddEventViewModel(private val repository: AppRepository) : ViewModel() {

     private val _savingTriggered = MutableLiveData<Boolean>(false)
     val savingTriggered: LiveData<Boolean> = _savingTriggered
     private val _savedSuccessfully = MutableLiveData<Boolean>()
     val savedSuccessfully: LiveData<Boolean> = _savedSuccessfully

     fun triggerSaving() {
         _savingTriggered.value = true
     }

     fun saveEvent(
         name: String,
         dateAndTime : LocalDateTime,
         description: String
     ) {
        val event = NewEvent(name = name,
            dateAndTime = dateAndTime,
            description = description)

         if (event != null) {
             viewModelScope.launch(Dispatchers.Main) {
                 repository.uploadNewEvent(event)
                 onSaveSucceed()
             }
         }else{
             onSaveFailed()
         }

     }

     private fun onSaveFailed() {
         _savedSuccessfully.value = false
     }

     private fun onSaveSucceed() {
         _savedSuccessfully.value = true
     }
}