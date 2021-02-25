package com.afkoders.testtask25feb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afkoders.testtask25feb.R
import com.afkoders.testtask25feb.databinding.FragmentUsersBinding
import com.afkoders.testtask25feb.domain.models.User
import com.afkoders.testtask25feb.ui.adapter.UserClick
import com.afkoders.testtask25feb.ui.adapter.UsersAdapter
import com.afkoders.testtask25feb.viewmodel.UsersViewModel

/**
 * Created by Kalevych Oleksandr on 25.02.2021.
 */

class UsersListFragment : Fragment() {

    private val viewModel: UsersViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, UsersViewModel.Factory(activity.application))
            .get(UsersViewModel::class.java)
    }

    /**
     * RecyclerView Adapter for converting a list of Video to cards.
     */
    private var viewModelAdapter: UsersAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.usersToDisplay.observe(viewLifecycleOwner, Observer<List<User>> { users ->
            if(users.isNullOrEmpty()){
                viewModel.refreshUsersFromNetwork()
            }
            users?.apply {
                viewModelAdapter?.users = users
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentUsersBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_users,
            container,
            false
        )
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        viewModelAdapter = UsersAdapter(UserClick ({
            // When a video is clicked this block or lambda will be called by DevByteAdapter
            val bundle = Bundle()
            bundle.putString(USER_ID_EXTRA, it.id)
            findNavController().navigate(R.id.action_users_to_userDetails, bundle)
        }, {
            viewModel.deleteUser(it.id)
        }))

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }


        // Observer for the network error.
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })

        return binding.root
    }

    /**
     * Method for displaying a Toast error message for network errors.
     */
    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }


    companion object{
        const val USER_ID_EXTRA = "USER_ID_EXTRA"
    }

}