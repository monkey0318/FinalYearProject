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
import com.example.finalyearproject.data.Sponsorship

class SponsorshipAdapter (
    val fn: (ViewHolder, Sponsorship) -> Unit = { _, _ -> }
) : ListAdapter<Sponsorship, SponsorshipAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Sponsorship>() {
        override fun areItemsTheSame(a: Sponsorship, b: Sponsorship) = a.Id == b.Id
        override fun areContentsTheSame(a: Sponsorship, b: Sponsorship) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val imgSponsorLogo      : ImageView = view.findViewById(R.id.imgSponsorLogo)
        val txtSponsorshipId    : TextView = view.findViewById(R.id.txtSponsorshipId)
        val txtSponsoredEvent   : TextView = view.findViewById(R.id.txtSponsoredEvent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rv_sponsorship, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sponsor = getItem(position)

        holder.txtSponsorshipId.text = sponsor.user_Id
        holder.txtSponsoredEvent.text   = sponsor.event_Id
        holder.imgSponsorLogo.setImageBitmap(sponsor.sponsor_Logo.toBitmap())


        fn(holder, sponsor)
    }

}