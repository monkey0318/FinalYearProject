package com.example.finalyearproject.event_management

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalyearproject.data.EventViewModel
import com.example.finalyearproject.databinding.FragmentEditEventBinding
import com.example.finalyearproject.util.cropToBlob
import com.example.finalyearproject.util.errorDialog
import com.example.finalyearproject.util.snackbar
import com.example.finalyearproject.util.toBitmap
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class EditEventFragment : Fragment() {

    private lateinit var binding: FragmentEditEventBinding
    private val nav by lazy { findNavController() }
    private val vm: EventViewModel by activityViewModels()
    private val db = Firebase.firestore
    private val formatter = SimpleDateFormat("dd MMMM yyyy '-' hh:mm:ss a", Locale.getDefault())
    private val id by lazy { requireArguments().getString("id") ?: "" }
//    private val geocoder by lazy { Geocoder(activity) }
    private val geocoder by lazy { activity?.let { Geocoder(it) } }


    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            binding.imgPhoto.setImageURI(it.data?.data)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentEditEventBinding.inflate(inflater, container, false)

        reset()
        binding.imgPhoto.setOnClickListener { select() }
        binding.btnReset.setOnClickListener { reset() }
        binding.btnConfirm.setOnClickListener { submit() }
        binding.btnDelete.setOnClickListener { delete() }

        return binding.root
    }

    private fun select() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset() {
        val ev = vm.get(id)
        if (ev == null) {
            nav.navigateUp()
            return
        }
        load(ev)
    }

    private fun load(ev: Event) {

        val addressList = geocoder!!.getFromLocation(ev.event_location.latitude,ev.event_location.longitude,1)
        val address = addressList?.get(0)

        binding.txtEventId.text = ev.event_id
        with(binding) {
            edtEventName.setText(ev.event_name)
            edtEventPhoneNum.setText(ev.event_phone)
            edtEventLocation.setText(address!!.getAddressLine(0))
            edtWebLink.setText(ev.event_website)
            edtDescription.setText(ev.event_description)
            imgPhoto.setImageBitmap(ev.event_photo.toBitmap())
            edtPrice.setText(ev.event_price.toString())
            edtEventDateTime.setText(ev.event_date)
            edtEventName.requestFocus()
        }
    }

    private fun submit() {

        val addressList = geocoder!!.getFromLocationName(binding.edtEventLocation.text.toString(), 1)
        val address = addressList?.get(0)
        val latLng = LatLng(address!!.latitude, address.longitude)

        val ev = Event(
            event_id          = id,
            event_name        = binding.edtEventName.text.toString().trim(),
            event_phone       = binding.edtEventPhoneNum.text.toString().trim(),
            event_location    = GeoPoint(address.latitude, address.longitude),
            event_website     = binding.edtWebLink.text.toString().trim(),
            event_description = binding.edtDescription.text.toString(),
            event_photo       = binding.imgPhoto.cropToBlob(300,300),
            event_price       = binding.edtPrice.text.toString().toDouble(),
            event_date        = binding.edtEventDateTime.text.toString().trim(),
            event_status      = binding.spEventStatus.selectedItem as String
        )
        val err = vm.validate(ev,false)
        snackbar("Event updated successfully")
        if (err != "") {
            errorDialog(err)
            return
        }

        vm.set(ev)
        nav.navigateUp()

    }

    private fun delete() {
        vm.delete(id)
        nav.navigateUp()

    }

}