package com.example.rest_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rest_app.Repo.Users

class MainAdapter(private val character: ArrayList<Users>):RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

 inner class  MainViewHolder(private val itemView: View, listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
     fun bindData(character: Users){
      val name = itemView.findViewById<TextView>(R.id.textView)
      val username = itemView.findViewById<TextView>(R.id.userEmail)

         name.text = character.name
         username.text = character.difficulty
     }
     init{

         itemView.setOnClickListener {
             listener.onItemClick(adapterPosition)
         }
     }

 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rc_view,parent,false),mListener)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
       holder.bindData(character[position])
    }

    override fun getItemCount(): Int {
        return character.size
    }

}