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
import com.example.finalyearproject.databinding.FragmentAddVoucherBinding
import com.example.finalyearproject.util.errorDialog
import com.example.finalyearproject.util.snackbar
import java.text.SimpleDateFormat
import java.util.*


class AddVoucherFragment : Fragment() {

    private lateinit var binding: FragmentAddVoucherBinding
    private val nav by lazy { findNavController() }
    private val vm: VoucherViewModel by activityViewModels()
    private val formatter = SimpleDateFormat("dd MMMM yyyy a", Locale.getDefault())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentAddVoucherBinding.inflate(inflater, container , false)

        reset()
        binding.btnReset.setOnClickListener { reset() }
        binding.btnAddVoucher.setOnClickListener { submit() }

        return binding.root
    }

    private fun submit() {
        val v = Voucher(
            voucher_name      = binding.edtVoucherName.text.toString().trim(),
            discount_amount   = binding.edtDiscAmount.text.toString().toDouble(),

        )

        val err = vm.validate(v)
        if (err != "") {
            errorDialog(err)
            return
        }

        vm.add(v)
        snackbar("Voucher added successfully")
        nav.navigateUp()
    }

    private fun reset() {
        binding.edtVoucherName.text.clear()
        binding.edtDiscAmount.text.clear()
        binding.edtVoucherName.requestFocus()
    }

}