package com.example.finalyearproject.communication

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
import com.example.finalyearproject.data.FaqViewModel
import com.example.finalyearproject.databinding.FragmentFaqBinding
import com.example.finalyearproject.util.FaqAdapter


class FaqFragment : Fragment() {

    private lateinit var binding: FragmentFaqBinding
    private val nav by lazy { findNavController() }
    private val vm: FaqViewModel by activityViewModels()

    private lateinit var adapter: FaqAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentFaqBinding.inflate(inflater, container, false)

        adapter = FaqAdapter() { holder, faq ->
            holder.root.setOnClickListener {
                nav.navigate(R.id.editFaqFragment, bundleOf("id" to faq.Question_Id))

            }

        }
        binding.rvQuestion.adapter = adapter
        binding.rvQuestion.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.faq_results.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
//            binding.txtCount.text = "${list.size} user(s)"
        }


        binding.svQuestion.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(question: String) = true
            override fun onQueryTextChange(question: String): Boolean {

                vm.search(question)
                return true
            }
        })


        return binding.root
    }

}