package com.afkoders.testtask25feb.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.afkoders.testtask25feb.data.network.models.asDatabaseModel
import com.afkoders.testtask25feb.data.database.UsersDatabase
import com.afkoders.testtask25feb.data.database.asDomainModel
import com.afkoders.testtask25feb.data.network.ButtonActionServiceImpl
import com.afkoders.testtask25feb.domain.models.User
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

    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {

            val usersFromNetwork =
                ButtonActionServiceImpl.usersServiceImpl.getUsers()


            database.usersDao.insertAll(usersFromNetwork.results.asDatabaseModel())
        }
    }


}