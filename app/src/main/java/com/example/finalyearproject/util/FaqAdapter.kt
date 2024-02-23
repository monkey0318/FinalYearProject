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
import com.example.finalyearproject.data.Faq

class FaqAdapter (
    val fn: (ViewHolder, Faq) -> Unit = { _, _ -> }
) : ListAdapter<Faq, FaqAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Faq>() {
        override fun areItemsTheSame(a: Faq, b: Faq) = a.Question_Id == b.Question_Id
        override fun areContentsTheSame(a: Faq, b: Faq) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val txtQuestion: TextView = view.findViewById(R.id.txtQuestion)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.question_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val faq = getItem(position)

        holder.txtQuestion.text = faq.Question


        fn(holder, faq)
    }
}