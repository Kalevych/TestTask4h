package com.afkoders.testtask25feb.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.afkoders.testtask25feb.domain.models.User

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

@Entity(tableName = "UsersDb")
data class UserDbModel constructor(
    @PrimaryKey
    val uuid: String,
    val firstName: String,
    val lastName: String,
    val photo: String,
    val email: String,
    val phone: String
)


/**
 * Map DatabaseVideos to domain entities
 */
fun List<UserDbModel>.asDomainModel(): List<User> {
    return map {
        User(
            it.uuid,
            it.firstName,
            it.lastName,
            it.photo,
            it.email,
            it.phone
        )
    }
}
