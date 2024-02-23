package com.example.finalyearproject.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalyearproject.R
import com.example.finalyearproject.data.Voucher
import com.example.finalyearproject.data.VoucherViewModel
import com.example.finalyearproject.databinding.FragmentEditVoucherBinding
import com.example.finalyearproject.util.errorDialog
import com.example.finalyearproject.util.snackbar


class EditVoucherFragment : Fragment() {

    private lateinit var binding: FragmentEditVoucherBinding
    private val nav by lazy { findNavController() }
    private val vm: VoucherViewModel by activityViewModels()

    private val id by lazy { requireArguments().getString("id") ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentEditVoucherBinding.inflate(inflater, container, false)
        reset()
        binding.btnResetVoucher.setOnClickListener { reset() }
        binding.btnEditVoucher.setOnClickListener { submit() }
        binding.btnDeleteVoucher.setOnClickListener { delete() }

        return binding.root

    }

    private fun delete() {
        vm.delete(id)
        nav.navigateUp()
    }

    private fun submit() {
        val v = Voucher(
            Id                = id,
            voucher_name      = binding.edtVoucherName.text.toString().trim(),
            discount_amount   = binding.edtDiscAmount.text.toString().toDouble(),

            )

        val err = vm.validate(v, false)
        if (err != "") {
            errorDialog(err)
            return
        }

        vm.set(v)
        snackbar("Event updated successfully")
        nav.navigateUp()
    }

    private fun reset() {
        val v = vm.get(id)
        if (v == null) {
            nav.navigateUp()
            return
        }
        load(v)
    }

    private fun load(v: Voucher) {

        binding.edtVoucherName.setText(v.voucher_name)
        binding.edtDiscAmount.setText(v.discount_amount.toString())
        binding.edtVoucherName.requestFocus()

    }
}