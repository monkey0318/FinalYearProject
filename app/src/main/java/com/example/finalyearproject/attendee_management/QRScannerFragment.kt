//package com.example.finalyearproject.attendee_management
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.SearchView
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.core.os.bundleOf
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.DividerItemDecoration
//import com.example.finalyearproject.R
//import com.example.finalyearproject.data.AttendeeViewModel
//import com.example.finalyearproject.data.EventViewModel
//import com.example.finalyearproject.data.Sales
//import com.example.finalyearproject.data.User
//import com.example.finalyearproject.databinding.FragmentQRScannerBinding
//import com.example.finalyearproject.util.AttendeeAdapter
//import com.example.finalyearproject.util.EventAdapter
//import com.example.finalyearproject.util.informationDialog
//import com.example.finalyearproject.util.snackbar
//import com.google.android.material.snackbar.Snackbar
//import com.google.common.util.concurrent.ListenableFuture
//import com.google.firebase.firestore.FieldValue
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
////import com.google.zxing.integration.android.IntentIntegrator
//import java.util.concurrent.ExecutorService
//
//
//class QRScannerFragment : Fragment() {
//
//    private lateinit var binding: FragmentQRScannerBinding
//    private val nav by lazy {findNavController()}
//    private val vm: AttendeeViewModel by activityViewModels()
//    private lateinit var adapter: AttendeeAdapter
//    private val db = Firebase.firestore
//
//    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//
//        val content = IntentIntegrator.parseActivityResult(it.resultCode, it.data).contents
//        if (content == null) {
//        }
//        else {
//            val paymentId = content
//
//
//                db.collection("Payment").document(paymentId).get()
//                    .addOnSuccessListener {
////
//                            val thisPayments = it.toObject(Sales::class.java)
//                            val payment = thisPayments
//                            if (payment!!.total_Ticket > payment!!.used_tickets) {
//
//                                db.collection("Payment").document(paymentId)
//                                    .update("used_tickets", FieldValue.increment(1))
//
//
//                                informationDialog("Valid ticket. \nAttendance already taken")
//
//                                snackbar("Ticket is registered")
//
//
//
//                            }
//                            else {
//                                informationDialog("Invalid Ticket. \nOr\nTicket(s) is used ")
//                                snackbar("Invalid Ticket")
//
//                            }
////
//
//                    }
//        }
//
//
//    }
//
//
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
//
//        binding = FragmentQRScannerBinding.inflate(inflater, container, false)
//
//        adapter = AttendeeAdapter() { holder, attd ->
//            holder.root.setOnClickListener {
////                nav.navigate(R.id.editEventFragment, bundleOf("id" to attd.event_Id))
//
//            }
//
//        }
//        binding.rvAttendee.adapter = adapter
//        binding.rvAttendee.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
//
//        vm.attendee_results.observe(viewLifecycleOwner) { list ->
//            adapter.submitList(list)
////            binding.txtCountEvent.text = "${list.size} event(s)"
//        }
//
//
//        binding.svAttendee.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(attd_event: String) = true
//            override fun onQueryTextChange(attd_event: String): Boolean {
//
//                vm.search(attd_event)
//                return true
//            }
//        })
//
//        binding.btnScan.setOnClickListener { scanQR() }
//
//
//
//
//        return binding.root
//    }
//
//    private fun scanQR() {
//        val intent = IntentIntegrator.forSupportFragment(this)
//            .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
//            .setPrompt("Scan QR Code\n")
//            .setBeepEnabled(true)
//            .createScanIntent()
//
//        getResult.launch(intent)
//    }
//
//
//}