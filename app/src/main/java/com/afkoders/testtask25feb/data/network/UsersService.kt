package com.afkoders.testtask25feb.data.network

import com.afkoders.testtask25feb.data.network.models.UsersResponse
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by Kalevych Oleksandr on 18.02.2021.
 */


interface UsersService{
    @GET("api/?results=30")
    suspend fun getUsers(): UsersResponse
}

/**
 * Main entry point for network access.
 */
object ButtonActionServiceImpl {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val usersServiceImpl = retrofit.create(UsersService::class.java)

    // should be handled by dagger
}