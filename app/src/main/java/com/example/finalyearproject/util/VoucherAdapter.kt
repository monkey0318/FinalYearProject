package com.example.finalyearproject.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import com.example.finalyearproject.data.Voucher

class VoucherAdapter (
    val fn: (ViewHolder, Voucher) -> Unit = { _, _ -> }
) : ListAdapter<Voucher, VoucherAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Voucher>() {
        override fun areItemsTheSame(a: Voucher, b: Voucher) = a.Id == b.Id
        override fun areContentsTheSame(a: Voucher, b: Voucher) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val txtVoucherName: TextView = view.findViewById(R.id.txtVoucherName)
        val txtDiscAmount: TextView = view.findViewById(R.id.txtDiscAmount)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.voucher_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val voucher = getItem(position)

        holder.txtVoucherName.text = voucher.voucher_name
        holder.txtDiscAmount.text = voucher.discount_amount.toString()


        fn(holder, voucher)
    }
}
