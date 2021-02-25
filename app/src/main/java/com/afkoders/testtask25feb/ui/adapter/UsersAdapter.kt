package com.afkoders.testtask25feb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afkoders.testtask25feb.R
import com.afkoders.testtask25feb.databinding.UserItemBinding
import com.afkoders.testtask25feb.domain.models.User

/**
 * Created by Kalevych Oleksandr on 25.02.2021.
 */

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class UsersAdapter(val callback: UserClick) : RecyclerView.Adapter<UserViewHolder>() {

    /**
     * The videos that our Adapter will show
     */
    var users: List<User> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val withDataBinding: UserItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            UserViewHolder.LAYOUT,
            parent,
            false)
        return UserViewHolder(withDataBinding)
    }

    override fun getItemCount() = users.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.user = users[position]
            it.userCallback = callback
        }
    }

}

/**
 * ViewHolder for DevByte items. All work is done by data binding.
 */
class UserViewHolder(val viewDataBinding: UserItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.user_item
    }
}