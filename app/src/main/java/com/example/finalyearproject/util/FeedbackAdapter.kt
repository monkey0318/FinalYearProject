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
import com.example.finalyearproject.data.Feedback

class FeedbackAdapter (
    val fn: (ViewHolder, Feedback) -> Unit = { _, _ -> }
) : ListAdapter<Feedback, FeedbackAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Feedback>() {
        override fun areItemsTheSame(a: Feedback, b: Feedback)    = a.Feedback_Id == b.Feedback_Id
        override fun areContentsTheSame(a: Feedback, b: Feedback) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val txtEventName         : TextView = view.findViewById(R.id.txtFeedbackEvent)
        val txtCustomerId        : TextView = view.findViewById(R.id.txtCustomerID)
        val txtCustFeedback      : TextView = view.findViewById(R.id.txtCustFeedback)
        val btnDelete            : Button = view.findViewById(R.id.btnDeleteFeedback)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.rv_feedback, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedback = getItem(position)

        holder.txtEventName.text = feedback.event_id
        holder.txtCustFeedback.text = feedback.feedback_text
        holder.txtCustomerId.text   = feedback.user_id



        fn(holder, feedback)
    }

}