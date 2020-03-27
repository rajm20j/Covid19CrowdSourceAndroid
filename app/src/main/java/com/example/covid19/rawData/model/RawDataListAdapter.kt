package com.example.covid19.rawData.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19.R

class RawDataListAdapter() : RecyclerView.Adapter<RawDataListAdapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var listItems: MutableList<RawDataModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.raw_data_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bannerText.text = listItems[position].raw_data[0].notes
    }


    override fun getItemCount(): Int {
        return listItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rlLayout = itemView.findViewById(R.id.theBigView) as LinearLayout
        val bannerText = itemView.findViewById(R.id.raw_data_notes) as TextView
    }

    constructor(context: Context, listItems: MutableList<RawDataModel>) : this() {
        this.context = context
        this.listItems = listItems
    }
}