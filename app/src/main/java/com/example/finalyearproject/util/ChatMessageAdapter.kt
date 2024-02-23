package com.example.finalyearproject.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.R
import com.example.finalyearproject.data.Message


class ChatMessageAdapter (val user_Id:String,
                      val fn:(ViewHolder, Message) -> Unit = { _, _ -> }
): ListAdapter<Message, ChatMessageAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.Text_Id == newItem.Text_Id

        override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val txtMessage: TextView = view.findViewById(R.id.txtMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.txtMessage.text = item.Text_Message
        holder.txtMessage.textAlignment = if(item.Sender_Id==user_Id) View.TEXT_ALIGNMENT_TEXT_END else View.TEXT_ALIGNMENT_TEXT_START
        fn(holder,item)
    }
}