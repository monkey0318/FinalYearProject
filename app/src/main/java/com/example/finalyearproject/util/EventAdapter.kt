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
import com.example.finalyearproject.event_management.Event



class EventAdapter (
    val fn: (ViewHolder, Event) -> Unit = { _, _ -> }
) : ListAdapter<Event, EventAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(a: Event, b: Event) = a.event_id == b.event_id
        override fun areContentsTheSame(a: Event, b: Event) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val imgEventPhoto : ImageView = view.findViewById(R.id.imgEventPhoto)
        val txtEventName         : TextView = view.findViewById(R.id.txtEvent)
        val txtEventPhone       : TextView = view.findViewById(R.id.txtEventPhone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.event_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)

        holder.txtEventName.text = event.event_name
        holder.txtEventPhone.text   = event.event_phone
        holder.imgEventPhoto.setImageBitmap(event.event_photo.toBitmap())


        fn(holder, event)
    }

    }