package com.example.finalyearproject.event_management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.finalyearproject.R
import com.example.finalyearproject.data.EventViewModel
import com.example.finalyearproject.data.SponsorshipViewModel
import com.example.finalyearproject.databinding.FragmentSponsorshipBinding
import com.example.finalyearproject.util.EventAdapter
import com.example.finalyearproject.util.SponsorshipAdapter


class SponsorshipFragment : Fragment() {

    private lateinit var binding : FragmentSponsorshipBinding
    private val nav by lazy { findNavController() }
    private val vm: SponsorshipViewModel by activityViewModels()

    private lateinit var adapter: SponsorshipAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentSponsorshipBinding.inflate(inflater, container, false)

        binding.btnAddSponsor.setOnClickListener { nav.navigate(R.id.addSponsorshipFragment) }



        adapter = SponsorshipAdapter() { holder, sponsor ->
            holder.root.setOnClickListener {
                nav.navigate(R.id.editSponsorshipFragment, bundleOf("id" to sponsor.Id))

            }

        }
        binding.rvSponsorship.adapter = adapter
        binding.rvSponsorship.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.sponsor_results.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.txtCountSponsor.text = "${list.size} sponsorship(s)"
        }


        binding.svSponsor.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String) = true
            override fun onQueryTextChange(name: String): Boolean {

                vm.search(name)
                return true
            }
        })

        return binding.root
    }


}