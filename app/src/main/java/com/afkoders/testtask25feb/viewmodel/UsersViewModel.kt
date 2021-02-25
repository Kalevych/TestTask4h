package com.afkoders.testtask25feb.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.afkoders.testtask25feb.data.database.getDatabase
import com.afkoders.testtask25feb.repository.UsersRepository
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val usersRepository = UsersRepository(getDatabase(application))

    val fetchedUsers = usersRepository.fetchedUsers


    val usersToDisplay = Transformations.map(fetchedUsers) {
        _loading.value = false
        it
    }


    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    private var _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    init {
        //TODO: add manual trigger
     //   refreshUsersFromNetwork()
    }


    fun refreshUsersFromNetwork() {
        viewModelScope.launch {
            try {
                _loading.value = true
                usersRepository.refreshUsers()
                _eventNetworkError.value = false

            } catch (networkError: IOException) {
                _eventNetworkError.value = true
                _loading.value = false
            }
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            usersRepository.deleteUser(userId)
        }
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UsersViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}