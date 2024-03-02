package com.example.finalyearproject.staff_management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.finalyearproject.R
import com.example.finalyearproject.data.StaffViewModel
import com.example.finalyearproject.databinding.FragmentStaffManagementBinding
import com.example.finalyearproject.util.UserAdapter

class StaffManagementFragment : Fragment() {

    private lateinit var binding: FragmentStaffManagementBinding
    private val nav by lazy { findNavController() }
    private val vm: StaffViewModel by activityViewModels()

    private lateinit var adapter: UserAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentStaffManagementBinding.inflate(inflater, container, false)

        binding.btnAddUser.setOnClickListener { nav.navigate(R.id.createUserFragment) }


        adapter = UserAdapter() { holder, user ->
            holder.root.setOnClickListener {
                nav.navigate(R.id.editUserFragment, bundleOf("id" to user.user_id))

            }

        }
        binding.rvUserList.adapter = adapter
        binding.rvUserList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.results.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.txtCount.text = "${list?.size} user(s)"
        }


        binding.svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String) = true
            override fun onQueryTextChange(name: String): Boolean {

                vm.search(name)
                return true
            }
        })
        return binding.root
    }


}