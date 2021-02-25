package com.afkoders.testtask25feb.ui.adapter

import com.afkoders.testtask25feb.domain.models.User

/**
 * Created by Kalevych Oleksandr on 25.02.2021.
 */

class UserClick(val block: (User) -> Unit) {
    fun onClick(user: User) = block(user)
}