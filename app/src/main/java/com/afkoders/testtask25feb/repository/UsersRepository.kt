package com.afkoders.testtask25feb.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.afkoders.testtask25feb.data.network.models.asDatabaseModel
import com.afkoders.testtask25feb.data.database.UsersDatabase
import com.afkoders.testtask25feb.data.database.asDomainModel
import com.afkoders.testtask25feb.data.network.ButtonActionServiceImpl
import com.afkoders.testtask25feb.domain.models.EmptyUser
import com.afkoders.testtask25feb.domain.models.User
import com.afkoders.testtask25feb.domain.models.toDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */


class UsersRepository(private val database: UsersDatabase) {

    val fetchedUsers: LiveData<List<User>> =
        Transformations.map(database.usersDao.getUsers()) {
            it.filter { it.uuid.isNotBlank() }.asDomainModel()
        }

     val currentUser: MutableLiveData<User> = MutableLiveData<User>(EmptyUser)


    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {

            val usersFromNetwork =
                ButtonActionServiceImpl.usersServiceImpl.getUsers()

            database.usersDao.deleteAndInsertAll(usersFromNetwork.results.asDatabaseModel())
        }
    }

   suspend fun checkSpecificUser(uuid: String) {
        withContext(Dispatchers.IO) {
            currentUser.postValue(
                database.usersDao.getUser(uuid).asDomainModel()
            )
        }
    }

    suspend fun updateUser(userId: String, firstName: String, lastName: String, phone: String, mail: String) {
        withContext(Dispatchers.IO) {
            database.usersDao.insert(
                User(
                    userId,
                    firstName,
                    lastName,
                    currentUser.value?.photoUrl ?: "",
                    mail,
                    phone
                ).toDbModel()
            )
        }
    }

    suspend fun deleteUser(userId: String) {
        withContext(Dispatchers.IO) {
            database.usersDao.deleteUser(userId)
        }
    }
}