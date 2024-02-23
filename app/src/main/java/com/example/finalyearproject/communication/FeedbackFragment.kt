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
import com.example.finalyearproject.data.FeedbackViewModel
import com.example.finalyearproject.databinding.FragmentFeedbackBinding
import com.example.finalyearproject.util.FeedbackAdapter
import com.example.finalyearproject.util.snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FeedbackFragment : Fragment() {

    private lateinit var binding : FragmentFeedbackBinding
    private val nav by lazy { findNavController() }
    private val vm: FeedbackViewModel by activityViewModels()

    private lateinit var adapter: FeedbackAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentFeedbackBinding.inflate(inflater, container, false)


        adapter = FeedbackAdapter() { holder, feedback ->

            holder.btnDelete.setOnClickListener { delete(feedback.Feedback_Id) }

        }
        binding.rvFeedback.adapter = adapter
        binding.rvFeedback.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.results.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.txtCountFeedback.text = "${list.size} feedback(s)"
        }


        binding.svFeedback.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String) = true
            override fun onQueryTextChange(name: String): Boolean {

                vm.search(name)
                return true
            }
        })



        return binding.root
    }

    private fun delete(id: String) {
       vm.delete(id)
        snackbar("Feedback Deleted!")
    }

}