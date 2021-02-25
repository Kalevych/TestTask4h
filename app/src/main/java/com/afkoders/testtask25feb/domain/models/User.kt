package com.afkoders.testtask25feb.domain.models

import com.afkoders.testtask25feb.data.database.UserDbModel
import java.util.*

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val photoUrl: String,
    val email: String,
    val phone: String
)


fun User.toDbModel() = UserDbModel(this.id, this.firstName, this.lastName, this.photoUrl, this.email, this.phone)


val EmptyUser = User("", "", "", "", "", "")
