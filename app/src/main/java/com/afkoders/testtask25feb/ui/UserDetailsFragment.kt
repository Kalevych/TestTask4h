package com.afkoders.testtask25feb.ui

/**
 * Created by Kalevych Oleksandr on 25.02.2021.
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.afkoders.testtask25feb.R
import com.afkoders.testtask25feb.databinding.FragmentUserDetailsBinding
import com.afkoders.testtask25feb.ui.UsersListFragment.Companion.USER_ID_EXTRA
import com.afkoders.testtask25feb.viewmodel.UserDetailsViewModel

/**
 * Created by Kalevych Oleksandr on 25.02.2021.
 */

class UsersDetailsFragment : Fragment() {

    private val viewModel: UserDetailsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, UserDetailsViewModel.Factory(activity.application))
            .get(UserDetailsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentUserDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_details,
            container,
            false
        )
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel
        val userId = arguments?.getString(USER_ID_EXTRA)?:""

        viewModel.getSpecificUser(userId)

        binding.root.findViewById<Button>(R.id.btn_edit_user).setOnClickListener{
            val bundle = Bundle()
            bundle.putString(USER_ID_EXTRA, userId)
            findNavController().navigate(R.id.action_usersDetails_to_userEdit, bundle)
        }
        /*binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }


        // Observer for the network error.
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })

         */

        return binding.root
    }

}