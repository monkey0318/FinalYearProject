package com.example.finalyearproject.communication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.finalyearproject.R
import com.example.finalyearproject.data.*
import com.example.finalyearproject.databinding.FragmentInvitationBinding
import com.example.finalyearproject.event_management.Event
import com.example.finalyearproject.util.*


class InvitationFragment : Fragment() {

    private lateinit var binding: FragmentInvitationBinding
    private val nav by lazy { findNavController() }
    private val vm: InvitationViewModel by activityViewModels()
    private lateinit var adapter: InvitationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View {

        binding = FragmentInvitationBinding.inflate(inflater, container, false)

        vm.events.observe(viewLifecycleOwner) { event ->
            val adp = ArrayAdapter(binding.spnEventList.context, android.R.layout.simple_spinner_item, event)
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spnEventList.adapter = adp
        }

        binding.spnEventList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                vm.setEventid((binding.spnEventList.selectedItem as Event).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        vm.customers.observe(viewLifecycleOwner) { customer ->
            val adp = ArrayAdapter(binding.spnCustList.context, android.R.layout.simple_spinner_item, customer)
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spnCustList.adapter = adp
        }

        binding.spnCustList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                vm.setCustid((binding.spnCustList.selectedItem as Customer).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        adapter = InvitationAdapter() { holder, invitation ->

            holder.btnDelete.setOnClickListener { delete(invitation.Invitation_Id) }

        }
        binding.rvInviteHistory.adapter = adapter
        binding.rvInviteHistory.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.invitation_results.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }


        binding.svInviteHistory.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String) = true
            override fun onQueryTextChange(name: String): Boolean {

                vm.search(name)
                return true
            }
        })


        binding.btnSendInvite.setOnClickListener { submit() }

        return binding.root
    }

    private fun delete(id: String) {
        vm.delete(id)
        snackbar("Invitation History Deleted!")
    }

    private fun submit() {
        val i = Invitation(
            event_id      = vm.eventId,
            user_id       = vm.customerId,


            )


        vm.add(i)
        snackbar("Invitation Sent")
    }


}