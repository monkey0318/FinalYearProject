package com.example.finalyearproject.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalyearproject.R
import com.example.finalyearproject.data.PaymentAckViewModel
import com.example.finalyearproject.data.Sales
import com.example.finalyearproject.data.Voucher
import com.example.finalyearproject.data.VoucherViewModel
import com.example.finalyearproject.databinding.FragmentPaymentAckDetailsBinding

class PaymentAckDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPaymentAckDetailsBinding
    private val nav by lazy { findNavController() }
    private val vm: PaymentAckViewModel by activityViewModels()
    private val id by lazy { requireArguments().getString("id") ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentPaymentAckDetailsBinding.inflate(inflater, container, false)

        reset()

        binding.btnDelete.setOnClickListener { delete() }

        return binding.root
    }

    private fun delete() {
        vm.delete(id)
        nav.navigateUp()
    }

    private fun reset() {
        val p = vm.get(id)
        if (p == null) {
            nav.navigateUp()
            return
        }
        load(p)
    }

    private fun load(p: Sales) {


        binding.txtPaymentId.text = p.Id
        binding.txtPaymentCustId.text = p.user_Id
        binding.txtPayEventID.text = p.event_Id
        binding.txtTicketPaid.text = p.total_Ticket.toString()
        binding.txtAmountPaid.text = p.payment_Price.toString()
        binding.txtPayDay.text = p.payment_Date.toString()

    }

}