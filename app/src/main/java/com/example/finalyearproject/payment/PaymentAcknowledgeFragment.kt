package com.example.finalyearproject.payment

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
import com.example.finalyearproject.data.PaymentAckViewModel
import com.example.finalyearproject.data.VoucherViewModel
import com.example.finalyearproject.databinding.FragmentPaymentAcknowledgeBinding
import com.example.finalyearproject.util.PaymentAckAdapter
import com.example.finalyearproject.util.VoucherAdapter


class PaymentAcknowledgeFragment : Fragment() {

    private lateinit var binding: FragmentPaymentAcknowledgeBinding
    private val nav by lazy { findNavController() }
    private val vm: PaymentAckViewModel by activityViewModels()
    private lateinit var adapter: PaymentAckAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentPaymentAcknowledgeBinding.inflate(inflater, container, false)

        adapter = PaymentAckAdapter() { holder, payment ->
            holder.root.setOnClickListener {
                nav.navigate(R.id.paymentAckDetailsFragment, bundleOf("id" to payment.Id))

            }

        }
        binding.rvUserPayment.adapter = adapter
        binding.rvUserPayment.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.payment_results.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
//            binding.txtCountVoucher.text = "${list.size} Voucher(s)"
        }


        binding.svUserPayment.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p_userId: String) = true
            override fun onQueryTextChange(p_userId: String): Boolean {

                vm.search(p_userId)
                return true
            }
        })

        return binding.root
    }

}