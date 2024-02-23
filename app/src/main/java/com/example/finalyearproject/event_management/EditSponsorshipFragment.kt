package com.example.finalyearproject.event_management

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalyearproject.R
import com.example.finalyearproject.data.Sponsorship
import com.example.finalyearproject.data.SponsorshipViewModel
import com.example.finalyearproject.data.Voucher
import com.example.finalyearproject.data.VoucherViewModel
import com.example.finalyearproject.databinding.FragmentEditSponsorshipBinding
import com.example.finalyearproject.util.cropToBlob
import com.example.finalyearproject.util.errorDialog
import com.example.finalyearproject.util.snackbar
import com.example.finalyearproject.util.toBitmap


class EditSponsorshipFragment : Fragment() {

    private lateinit var binding: FragmentEditSponsorshipBinding
    private val nav by lazy { findNavController() }
    private val vm: SponsorshipViewModel by activityViewModels()
    private val id by lazy { requireArguments().getString("id") ?: "" }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            binding.imgSponsorLogo.setImageURI(it.data?.data)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentEditSponsorshipBinding.inflate(inflater, container, false)

        vm.events.observe(viewLifecycleOwner) { Feedback ->
            val adp = ArrayAdapter(binding.spnEventList.context, android.R.layout.simple_spinner_item, Feedback)
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

        reset()
        binding.imgSponsorLogo.setOnClickListener { select() }
        binding.btnDeleteSponsor.setOnClickListener { delete() }
        binding.btnConfirmSponsor.setOnClickListener { submit() }
        binding.btnResetSponsor.setOnClickListener { reset() }

        return binding.root
    }

    private fun delete() {
        vm.delete(id)
        nav.navigateUp()
    }

    private fun submit() {
        val s = Sponsorship(
            Id              = id,
            event_Id        = vm.eventId,
            user_Id       = binding.edtSponsorshipId.text.toString().trim(),
            sponsor_Price = binding.edtSponsorPrice.text.toString().toDouble(),
            sponsor_Logo  = binding.imgSponsorLogo.cropToBlob(300,300)

            )

        val err = vm.validate(s, false)
        if (err != "") {
            errorDialog(err)
            return
        }

        vm.set(s)
        snackbar("Sponsorship updated successfully")
        nav.navigateUp()
    }

    private fun select() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset() {
        val s = vm.get(id)
        if (s == null) {
            nav.navigateUp()
            return
        }
        load(s)
    }

    private fun load(s: Sponsorship) {

        binding.txtSponsorID.text = s.Id
        binding.edtSponsorshipId.setText(s.user_Id)
        binding.edtSponsorPrice.setText(s.sponsor_Price.toString())
        binding.imgSponsorLogo.setImageBitmap(s.sponsor_Logo.toBitmap())
        binding.edtSponsorshipId.requestFocus()

    }
}