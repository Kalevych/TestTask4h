package com.afkoders.testtask25feb.data.network.models

import com.afkoders.testtask25feb.data.database.UserDbModel
import com.afkoders.testtask25feb.domain.models.User
import com.google.gson.annotations.SerializedName

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */

data class UsersResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<UserResult>
)

data class Info(
    val page: Int,
    val results: Int,
   // val seed: String,
   // val version: String
)

data class UserResult(
  //  val cell: String,
  //  val dob: Dob,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Id,
  //  val location: Location,
    @SerializedName("login")
    val login: Login,
    @SerializedName("name")
    val name: Name,
  //  val nat: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("picture")
    val picture: Picture,
//    val registered: Registered
)

data class Dob(
    val age: Int,
    val date: String
)

data class Id(
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String
)

data class Location(
    val city: String,
    val coordinates: Coordinates,
    val country: String,
    val postcode: Int,
    val state: String,
    val street: Street,
    val timezone: Timezone
)

data class Login(
   // val md5: String,
  //  val password: String,
  //  val salt: String,
  //  val sha1: String,
  //  val sha256: String,
  //  val username: String,
    val uuid: String
)

data class Name(
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String,
    @SerializedName("title")
    val title: String
)

data class Picture(
    @SerializedName("large")
    val large: String,
    @SerializedName("medium")
    val medium: String,
    @SerializedName("thumbnail")
    val thumbnail: String
)

data class Registered(
    val age: Int,
    val date: String
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Street(
    val name: String,
    val number: Int
)

data class Timezone(
    val description: String,
    val offset: String
)

fun List<UserResult>.asDomainModels(): List<User> {
    return this.map {
        User(
            it.login.uuid, it.name.first, it.name.last, it.picture.medium, it.email, it.phone
        )
    }
}


/**
 * Convert Network results to database objects
 */
fun List<UserResult>.asDatabaseModel(): List<UserDbModel> {
    return this.map {
        UserDbModel(
            it.login.uuid,
            it.name.first,
            it.name.last,
            it.picture.medium,
            it.email,
            it.phone
        )
    }
}