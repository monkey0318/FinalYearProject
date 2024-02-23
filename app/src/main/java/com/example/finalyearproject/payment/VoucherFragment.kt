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
import com.example.finalyearproject.data.VoucherViewModel
import com.example.finalyearproject.databinding.FragmentVoucherBinding
import com.example.finalyearproject.util.VoucherAdapter

class VoucherFragment : Fragment() {

    private lateinit var binding: FragmentVoucherBinding
    private val nav by lazy { findNavController() }
    private val vm: VoucherViewModel by activityViewModels()
    private lateinit var adapter: VoucherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {

        binding = FragmentVoucherBinding.inflate(inflater, container, false)

        binding.btnAddVoucher.setOnClickListener { nav.navigate(R.id.addVoucherFragment) }


        adapter = VoucherAdapter() { holder, voucher ->
            holder.root.setOnClickListener {
                nav.navigate(R.id.editVoucherFragment, bundleOf("id" to voucher.Id))

            }

        }
        binding.rvVoucherList.adapter = adapter
        binding.rvVoucherList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.voucher_results.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.txtCountVoucher.text = "${list.size} Voucher(s)"
        }


        binding.svVoucher.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String) = true
            override fun onQueryTextChange(name: String): Boolean {

                vm.search(name)
                return true
            }
        })


        return binding.root
    }

}