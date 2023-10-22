package com.riedera.events.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riedera.events.domain.AppRepository
import com.riedera.events.domain.models.Club
import com.riedera.events.domain.models.User
import com.riedera.events.presentation.adapters.ClubListAdapter
import com.riedera.events.presentation.fragments.ClubDetailsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(private val repository: AppRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _clubsList = MutableLiveData<List<Club>>()
    val clubsList: LiveData<List<Club>> = _clubsList
    var clubsAdapter: ClubListAdapter = ClubListAdapter(mutableListOf())


    init {
        viewModelScope.launch(Dispatchers.Main) {
            val list = repository.getAllClubs()
            if (ClubDetailsFragment.savedClubId != null) _clubsList.value =
                list.subList(1, 3) + repository.getClubById(ClubDetailsFragment.savedClubId!!)
            else _clubsList.value = list.subList(1, 3)
        }
    }

    private fun getUser(): User {
        return User(
            id = 1,
            login = "user",
            password = "password",
            subscribedEvents = emptyList(),
            token = "sdfsdf",
            clubs = emptyList()
        )
    }
}