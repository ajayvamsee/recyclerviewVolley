package com.ajayvamsee.recyclerviewvolley

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class UserAdapter(private var user: ArrayList<User>, private var context: Context) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userModel = user[position]
        holder.firstName.text = userModel.firstName
        holder.lastName.text = userModel.lastName
        holder.emailTV.text = userModel.email

        Picasso.get().load(userModel.avatar).into(holder.userIV)
    }

    override fun getItemCount(): Int {
        return user.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstName: TextView = itemView.findViewById(R.id.idTVFirstName)
        val lastName: TextView = itemView.findViewById(R.id.idTVLastName)
        val emailTV: TextView = itemView.findViewById(R.id.idTVEmail)
        val userIV: ImageView = itemView.findViewById(R.id.idIVUser)

    }
}