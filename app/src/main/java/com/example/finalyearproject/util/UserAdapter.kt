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
import com.example.finalyearproject.data.User


class UserAdapter (
    val fn: (ViewHolder, User) -> Unit = { _, _ -> }
) : ListAdapter<User, UserAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(a: User, b: User)    = a.user_id == b.user_id
        override fun areContentsTheSame(a: User, b: User) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val imgProfilePhoto : ImageView = view.findViewById(R.id.imgProfilePhoto)
        val txtName         : TextView = view.findViewById(R.id.txtName)
        val txtEmail        : TextView = view.findViewById(R.id.txtEmail)
        val txtRole         : TextView = view.findViewById(R.id.txtRole)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.user_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)

        holder.txtName.text = user.user_name
        holder.txtEmail.text   = user.user_email
        holder.txtRole.text  = user.user_role.toString()
        holder.imgProfilePhoto.setImageBitmap(user.user_photo.toBitmap())


        fn(holder, user)
    }

}