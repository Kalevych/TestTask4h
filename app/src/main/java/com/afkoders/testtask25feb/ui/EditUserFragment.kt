package com.afkoders.testtask25feb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.afkoders.testtask25feb.R
import com.afkoders.testtask25feb.databinding.FragmentEditUserBinding
import com.afkoders.testtask25feb.viewmodel.EditUserViewModel
import kotlinx.android.synthetic.main.fragment_edit_user.*

/**
 * Created by Kalevych Oleksandr on 25.02.2021.
 */


class EditUserFragment : Fragment() {

    private val viewModel: EditUserViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, EditUserViewModel.Factory(activity.application))
            .get(EditUserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEditUserBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_user,
            container,
            false
        )

        //could be avoided
        val btnUpdateUser = binding.root.findViewById<Button>(R.id.btn_update_user)
        val btnDeleteUser = binding.root.findViewById<Button>(R.id.btn_delete_user)
        val etFirstName = binding.root.findViewById<EditText>(R.id.et_first_name)
        val etLastName = binding.root.findViewById<EditText>(R.id.et_last_name)
        val etPhone = binding.root.findViewById<EditText>(R.id.et_phone_text)
        val etMail = binding.root.findViewById<EditText>(R.id.et_mail_text)

        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        val userId = arguments?.getString(UsersListFragment.USER_ID_EXTRA) ?: ""

        viewModel.apply {
            getSpecificUser(userId)

            userDeleted.observe(
                viewLifecycleOwner,
                Observer {
                    if (it) findNavController().popBackStack()
                })

            userUpdated.observe(
                viewLifecycleOwner,
                Observer {
                    if (it) tv_update_successfull.visibility = View.VISIBLE
                })
        }

        btnUpdateUser.setOnClickListener {
            viewModel.updateUser(
                userId,
                etFirstName.text.toString(),
                etLastName.text.toString(),
                etPhone.text.toString(),
                etMail.text.toString(),
            )
        }

        //TODO: a lot of duplication could be avoided

        etFirstName.addTextChangedListener {
            tv_update_successfull.visibility = View.GONE
        }

        etLastName.addTextChangedListener {
            tv_update_successfull.visibility = View.GONE
        }

        etPhone.addTextChangedListener {
            tv_update_successfull.visibility = View.GONE
        }

        etMail.addTextChangedListener {
            tv_update_successfull.visibility = View.GONE
        }

        btnDeleteUser.setOnClickListener {
            viewModel.deleteUser(userId)
        }

        return binding.root
    }

}