package com.afkoders.testtask25feb.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.afkoders.testtask25feb.data.database.getDatabase
import com.afkoders.testtask25feb.repository.UsersRepository
import kotlinx.coroutines.launch

/**
 * Created by Kalevych Oleksandr on 25.02.2021.
 */


class UserDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val usersRepository = UsersRepository(getDatabase(application))

    fun getSpecificUser(uuid: String) {
        viewModelScope.launch {
            usersRepository.checkSpecificUser(uuid)
        }
    }

    //TODO: duplication, this code smells a bit. Could be refactored
    val currentUser = usersRepository.currentUser

    val userPhoto = Transformations.map(currentUser) {
        it?.photoUrl ?: ""
    }
    val firstName = Transformations.map(currentUser) {
        it?.firstName ?: ""
    }
    val lastName = Transformations.map(currentUser) {
        it?.lastName ?: ""
    }
    val phone = Transformations.map(currentUser) {
        it?.phone ?: ""
    }
    val mail = Transformations.map(currentUser) {
        it?.email ?: ""
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            usersRepository.deleteUser(userId)
            userDeleted.postValue(true)
        }
    }

    val userDeleted = MutableLiveData<Boolean>(false)

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserDetailsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}