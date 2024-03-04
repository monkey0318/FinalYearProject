package com.example.finalyearproject.event_management

import android.app.*
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalyearproject.data.EventViewModel
import com.example.finalyearproject.databinding.FragmentTestingCreateEventBinding
import com.example.finalyearproject.util.cropToBlob
import com.example.finalyearproject.util.errorDialog
import com.example.finalyearproject.util.snackbar
import java.util.*
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import java.io.IOException


class TestingCreateEventFragment : Fragment() {
    private lateinit var binding: FragmentTestingCreateEventBinding
    private val nav by lazy { findNavController() }
    private val vm: EventViewModel by activityViewModels()
    private lateinit var alarmManager: AlarmManager
    private lateinit var calendar: Calendar
    private lateinit var pendingIntent: PendingIntent
    private val geocoder by lazy { activity?.let { Geocoder(it) } }


    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            binding.imgEventPhoto.setImageURI(it.data?.data)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentTestingCreateEventBinding.inflate(inflater, container, false)


        reset()
        binding.imgEventPhoto.setOnClickListener { select() }
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
        try {
            val addressList = geocoder?.getFromLocationName(binding.edtEventLocation.text.toString(), 1)
            if (addressList.isNullOrEmpty()) {
                // Handle the case where the location is not found
                Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show()
                return
            }
            val address = addressList[0]
            val latLng = LatLng(address.latitude, address.longitude)

            val ev = Event(
                event_id           = binding.edtEventId.text.toString().trim().uppercase(),
                event_name         = binding.edtEventName.text.toString().trim(),
                event_description  = binding.edtDescription.text.toString().trim(),
                event_location     = GeoPoint(address.latitude, address.longitude),
                event_phone        = binding.edtEventPhoneNum.text.toString().trim(),
                event_price        = binding.edtPrice.text.toString().toDouble(),
                event_website      = binding.edtWebLink.text.toString().trim(),
                event_photo        = binding.imgEventPhoto.cropToBlob(300,300),
                event_status       = binding.spEventStatus.selectedItem as String,
                event_date        = binding.edtEventDateTime.text.toString().trim(),
            )

            val err = vm.validate(ev)
            if (err != "") {
                errorDialog(err)
                return
            }

            vm.set(ev)
            snackbar("Event added successfully")
            nav.navigateUp()
        } catch (e: IOException) {
            // Handle the case where there are network connectivity issues or Geocoder service is unavailable
            Toast.makeText(requireContext(), "Error fetching location: ${e.message}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // Handle any other unexpected exceptions
            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun reset() {
        binding.edtEventId.text.clear()
        binding.edtEventName.text.clear()
        binding.edtDescription.text.clear()
        binding.edtEventLocation.text.clear()
        binding.edtEventPhoneNum.text.clear()
        binding.edtPrice.text.clear()
        binding.edtWebLink.text.clear()
        binding.imgEventPhoto.setImageDrawable(null)
        binding.edtEventDateTime.text.clear()
        binding.edtEventId.requestFocus()

    }

    private fun selectTime() {

        calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 17
        calendar[Calendar.MINUTE] = 5
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

    }

    private fun setAlarm() {
        alarmManager = activity?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(activity, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        val toast = Toast.makeText(activity, "Reminder set Successfully", Toast.LENGTH_SHORT).show()
    }



    private fun createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name : CharSequence = "foxandroidReminderChannel"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("deeznut" ,name, importance)
            channel. description = description
            val notificationManager = activity?.getSystemService(
                NotificationManager::class.java
            )

            notificationManager?.createNotificationChannel(channel)
        }

    }


}