package com.afkoders.testtask25feb.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.afkoders.testtask25feb.data.database.getDatabase
import com.afkoders.testtask25feb.repository.UsersRepository
import kotlinx.coroutines.launch

/**
 * Created by Kalevych Oleksandr on 25.02.2021.
 */

class EditUserViewModel(application: Application) : AndroidViewModel(application) {

    private val usersRepository = UsersRepository(getDatabase(application))

    fun getSpecificUser(uuid: String) {
        viewModelScope.launch {
            usersRepository.checkSpecificUser(uuid)
        }
    }

    fun updateUser(
        userId: String,
        firstName: String,
        lastName: String,
        phone: String,
        mail: String
    ) {
        viewModelScope.launch {
            usersRepository.updateUser(userId, firstName, lastName, phone, mail)
            userUpdated.postValue(true)
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            usersRepository.deleteUser(userId)
            userDeleted.postValue(true)
        }
    }

    //TODO: half-crutches. Could be handled in better way
    val userDeleted = MutableLiveData<Boolean>(false)
    val userUpdated = MutableLiveData<Boolean>(false)


    //TODO: duplication, this code smells a bit. Could be refactored
    val currentUser = usersRepository.currentUser

    val firstName = Transformations.map(currentUser) {
        it?.firstName ?: ""
    }
    val lastName = Transformations.map(currentUser) {
        it?.lastName ?: ""
    }
    val phone = Transformations.map(currentUser) {
        it?.phone ?: ""
    }
    val email = Transformations.map(currentUser) {
        it?.email ?: ""
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditUserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EditUserViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}