package com.example.covid19.home.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19.R
import com.example.covid19.home.newModel.dataV4.StateData




class HomeStatsListAdapter() : RecyclerView.Adapter<HomeStatsListAdapter.ViewHolder>() {

    lateinit var context: Context
    var listItems: HashMap<String?, StateData?>? = null
    var keyItem: MutableList<String?>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position%2 != 0)
            holder.linearLayout.setBackgroundColor(context.resources.getColor(R.color.almost_almost_white, null))
        holder.bannerText.text = keyItem?.get(position)!!
    }


    override fun getItemCount(): Int {
        return listItems?.size!!
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linearLayout = itemView.findViewById(R.id.constraintLayout) as LinearLayout
        val bannerText = itemView.findViewById(R.id.home_list_item_name) as TextView
    }

    constructor(context: Context, listItems: HashMap<String?, StateData?>?) : this() {
        this.context = context
        this.listItems = listItems
        val keyItem: MutableList<String?> = mutableListOf()
        listItems?.forEach { (key, _) -> keyItem.add(key) }
        this.keyItem = keyItem
    }
}