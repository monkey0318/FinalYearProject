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
import com.example.finalyearproject.data.EventViewModel
import com.example.finalyearproject.data.Sponsorship
import com.example.finalyearproject.data.SponsorshipViewModel
import com.example.finalyearproject.databinding.FragmentAddSponsorshipBinding
import com.example.finalyearproject.util.cropToBlob
import com.example.finalyearproject.util.errorDialog
import com.example.finalyearproject.util.snackbar
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint


class AddSponsorshipFragment : Fragment() {

    private lateinit var binding: FragmentAddSponsorshipBinding
    private val nav by lazy { findNavController() }
    private val vm: SponsorshipViewModel by activityViewModels()

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            binding.imgSponsorLogo.setImageURI(it.data?.data)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentAddSponsorshipBinding.inflate(inflater, container, false)

        vm.events.observe(viewLifecycleOwner) { event ->
            val adp = ArrayAdapter(binding.spEventList.context, android.R.layout.simple_spinner_item, event)
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spEventList.adapter = adp
        }

        binding.spEventList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                vm.setEventid((binding.spEventList.selectedItem as Event).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        reset()
        binding.imgSponsorLogo.setOnClickListener { select() }
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }

        return binding.root
    }


    private fun select() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun submit() {


        val s = Sponsorship(
            event_Id        = vm.eventId,
            user_Id         = binding.edtSponsorshipId.text.toString().trim(),
            sponsor_Price   = binding.edtSponsorPrice.text.toString().toDouble(),
            sponsor_Logo    = binding.imgSponsorLogo.cropToBlob(300,300),

        )

        val err = vm.validate(s)

        if (err != "") {
            errorDialog(err)
            return
        }
        vm.add(s)
        snackbar("Sponsorship added successfully")
        nav.navigateUp()
    }

    private fun reset() {
        binding.edtSponsorshipId.text.clear()
        binding.edtSponsorPrice.text.clear()
        binding.imgSponsorLogo.setImageDrawable(null)
        binding.edtSponsorshipId.requestFocus()
    }


}