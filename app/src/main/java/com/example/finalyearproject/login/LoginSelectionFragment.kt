package com.example.finalyearproject.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalyearproject.R
import com.example.finalyearproject.databinding.FragmentLoginSelectionBinding
//import com.example.finalyearproject.user.db.restore_user


class LoginSelectionFragment : Fragment() {
    private lateinit var binding: FragmentLoginSelectionBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentLoginSelectionBinding.inflate(inflater, container, false)

        binding.btnAdmin.setOnClickListener { nav.navigate(R.id.adminLoginPageFragment) }
        binding.btnUser.setOnClickListener { nav.navigate(R.id.userLoginPageFragment) }

        return binding.root
    }

//    private fun restore() {
//        val ctx = requireContext()
//        restore_user(ctx)
//    }
}