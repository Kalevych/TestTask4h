package com.afkoders.testtask25feb.domain.models

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
