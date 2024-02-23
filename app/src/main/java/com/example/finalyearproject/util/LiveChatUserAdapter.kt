package com.example.finalyearproject.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import com.example.finalyearproject.data.Customer

class LiveChatUserAdapter (
    val fn:(ViewHolder, Customer) -> Unit = { _, _ -> }
): ListAdapter<Customer, LiveChatUserAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer) = oldItem == newItem
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val txtAdmin: TextView = view.findViewById(R.id.txtAdmin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.txtAdmin.text = item.name
        fn(holder,item)
    }
}

