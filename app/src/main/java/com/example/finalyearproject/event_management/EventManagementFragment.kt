package com.example.finalyearproject.event_management

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
import com.example.finalyearproject.data.EventViewModel
import com.example.finalyearproject.databinding.FragmentEventManagementBinding
import com.example.finalyearproject.util.EventAdapter

class EventManagementFragment : Fragment() {

    private lateinit var binding: FragmentEventManagementBinding
    private val nav by lazy { findNavController() }
    private val vm: EventViewModel by activityViewModels()

    private lateinit var adapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View {
        binding = FragmentEventManagementBinding.inflate(inflater,container, false)

        binding.btnAddEvent.setOnClickListener { nav.navigate(R.id.testingCreateEventFragment)}
        binding.btnSponsorship.setOnClickListener { nav.navigate(R.id.sponsorshipFragment)}


        adapter = EventAdapter() { holder, event ->
            holder.root.setOnClickListener {
                nav.navigate(R.id.editEventFragment, bundleOf("id" to event.event_id))

            }

        }
        binding.rvEventList.adapter = adapter
        binding.rvEventList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.event_results.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.txtCountEvent.text = "${list.size} event(s)"
        }


        binding.svEvent.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String) = true
            override fun onQueryTextChange(name: String): Boolean {

                vm.search(name)
                return true
            }
        })


        return binding.root
    }

}
















