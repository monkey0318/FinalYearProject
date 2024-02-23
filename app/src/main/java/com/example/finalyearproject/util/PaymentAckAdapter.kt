package com.example.finalyearproject.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import com.example.finalyearproject.data.Sales

class PaymentAckAdapter (
    val fn: (ViewHolder, Sales) -> Unit = { _, _ -> }
) : ListAdapter<Sales, PaymentAckAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Sales>() {
        override fun areItemsTheSame(a: Sales, b: Sales) = a.Id == b.Id
        override fun areContentsTheSame(a: Sales, b: Sales) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val txtPaymentID: TextView = view.findViewById(R.id.txtPaymentID)
        val txtUserId: TextView = view.findViewById(R.id.txtUserId)
        val txtPaymentDate: TextView = view.findViewById(R.id.txtPaymentDate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rv_payment_ack, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val payment = getItem(position)

        holder.txtPaymentID.text = payment.Id
        holder.txtUserId.text = payment.user_Id
        holder.txtPaymentDate.text = payment.payment_Date.toString()


        fn(holder, payment)
    }
}