package com.example.finalyearproject.communication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalyearproject.R
import com.example.finalyearproject.data.Faq
import com.example.finalyearproject.data.FaqViewModel
import com.example.finalyearproject.databinding.FragmentEditFaqBinding
import com.example.finalyearproject.util.errorDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EditFaqFragment : Fragment() {

    private lateinit var binding: FragmentEditFaqBinding
    private val nav by lazy { findNavController() }
    private val vm: FaqViewModel by activityViewModels()
    private val db = Firebase.firestore

    private val id by lazy { requireArguments().getString("id") ?: "" }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentEditFaqBinding.inflate(inflater, container, false)

        reset()
        binding.btnReset.setOnClickListener { reset() }
        binding.btnUpdate.setOnClickListener { submit() }

        return binding.root
    }

    private fun submit() {
        val q = Faq(
            Question_Id = id,
            Question = binding.edtQuestion.text.toString().trim(),
            Answer = binding.edtAnswer.text.toString().trim(),

        )
        val err = vm.validate(q,false)
        if (err != "") {
            errorDialog(err)
            return
        }

        vm.set(q)
        nav.navigateUp()
    }

    private fun delete() {
        vm.delete(id)
        nav.navigateUp()
    }

    private fun reset() {
        val q = vm.get(id)
        if (q == null) {
            nav.navigateUp()
            return
        }
        load(q)
    }

    private fun load(q: Faq) {

        binding.edtQuestion.setText(q.Question)
        binding.edtAnswer.setText(q.Answer)

    }

}