package com.example.covid19.travelHistory.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19.R

class TravelListAdapter() : RecyclerView.Adapter<TravelListAdapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var listItems: MutableList<String>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.travel_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bannerText.text = listItems[position]
    }


    override fun getItemCount(): Int {
        return listItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rlLayout = itemView.findViewById(R.id.theBigView) as LinearLayout
        val bannerText = itemView.findViewById(R.id.home_list_item_name) as TextView
    }

    constructor(context: Context, listItems: MutableList<String>) : this() {
        this.context = context
        this.listItems = listItems
    }
}