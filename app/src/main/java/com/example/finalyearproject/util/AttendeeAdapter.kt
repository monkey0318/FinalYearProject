package com.example.finalyearproject.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import com.example.finalyearproject.data.Sales

class AttendeeAdapter (
    val fn: (ViewHolder, Sales) -> Unit = { _, _ -> }
) : ListAdapter<Sales, AttendeeAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Sales>() {
        override fun areItemsTheSame(a: Sales, b: Sales) = a.Id == b.Id
        override fun areContentsTheSame(a: Sales, b: Sales) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val txtPresentAttendee        : TextView = view.findViewById(R.id.txtPresentAttendee)
        val txtAttendeeEvent        : TextView = view.findViewById(R.id.txtAttendeeEvent)
        val txtTotalTickets        : TextView = view.findViewById(R.id.txtTotalTickets)
        val txtAttendeeCust        : TextView = view.findViewById(R.id.txtAttendeeCust)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rv_attendee_manage, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val attendee = getItem(position)

        holder.txtPresentAttendee.text = attendee.used_tickets.toString()
        holder.txtTotalTickets.text = attendee.total_Ticket.toString()
        holder.txtAttendeeEvent.text   = attendee.event_Id
        holder.txtAttendeeCust.text   = attendee.user_Id


        fn(holder, attendee)
    }

}