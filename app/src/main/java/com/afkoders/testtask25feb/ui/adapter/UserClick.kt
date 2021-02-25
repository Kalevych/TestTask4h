package com.afkoders.testtask25feb.ui.adapter

import com.afkoders.testtask25feb.domain.models.User

/**
 * Created by Kalevych Oleksandr on 25.02.2021.
 */

class UserClick(val blockUser: (User) -> Unit, val blockDelete: (User) -> Unit) {
    fun onClickUser(user: User) = blockUser(user)
    fun onClickDelete(user: User) = blockDelete(user)
}