package com.example.finalyearproject.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import com.example.finalyearproject.data.Invitation
import com.example.finalyearproject.data.Sales

class InvitationAdapter (
    val fn: (ViewHolder, Invitation) -> Unit = { _, _ -> }
) : ListAdapter<Invitation, InvitationAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Invitation>() {
        override fun areItemsTheSame(a: Invitation, b: Invitation) = a.Invitation_Id == b.Invitation_Id
        override fun areContentsTheSame(a: Invitation, b: Invitation) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val txtInviteCust        : TextView = view.findViewById(R.id.txtInviteCust)
        val txtInviteEvent        : TextView = view.findViewById(R.id.txtInviteEvent)
        val btnDelete            : Button = view.findViewById(R.id.btnDeleteInviteHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rv_invitation_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val invitation = getItem(position)

        holder.txtInviteEvent.text = invitation.event_id
        holder.txtInviteCust.text = invitation.user_id


        fn(holder, invitation)
    }

}