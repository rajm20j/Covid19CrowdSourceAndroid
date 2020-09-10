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
import com.example.covid19.utils.Utils


class HomeStatsListAdapter() : RecyclerView.Adapter<HomeStatsListAdapter.ViewHolder>() {

    lateinit var context: Context
    var listItems: HashMap<String?, StateData?>? = null
    var keyItem: MutableList<String?>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position%2 == 0)
            holder.linearLayout.setBackgroundColor(context.resources.getColor(R.color.almost_almost_white, null))
        holder.bannerText.text = keyItem?.get(position)!!
        holder.bannerText.setBackgroundColor(context.resources.getColor(R.color.almost_almost_white, null))

        if(position == 0) {
            holder.linearLayout.setBackgroundColor(context.resources.getColor(R.color.almost_black, null))
            holder.bannerText.setBackgroundColor(context.resources.getColor(R.color.almost_black, null))

            holder.bannerText.text = context.resources.getString(R.string.stateut)
            holder.bannerText.setTextColor(context.resources.getColor(R.color.almost_white, null))

            holder.confirmed.text = context.resources.getString(R.string.confirmed)
            holder.confirmed.setTextColor(context.resources.getColor(R.color.almost_white, null))

            holder.active.text = context.resources.getString(R.string.active)
            holder.active.setTextColor(context.resources.getColor(R.color.almost_white, null))

            holder.recovered.text = context.resources.getString(R.string.recovered)
            holder.recovered.setTextColor(context.resources.getColor(R.color.almost_white, null))

            holder.deceased.text = context.resources.getString(R.string.deceased)
            holder.deceased.setTextColor(context.resources.getColor(R.color.almost_white, null))

            return
        }

        val confirmed = listItems?.get(keyItem?.get(position)!!)?.total?.confirmed
        val recovered = listItems?.get(keyItem?.get(position)!!)?.total?.recovered
        val  deceased = listItems?.get(keyItem?.get(position)!!)?.total?.deceased

        if(confirmed != null)
            holder.confirmed.text = Utils.formatNumber(confirmed)

        if(confirmed != null && recovered != null && deceased != null)
        {
            val act = confirmed.minus(recovered).minus(deceased)
            holder.active.text = Utils.formatNumber(act)
        }

        if(recovered != null)
            holder.recovered.text = Utils.formatNumber(recovered)

        if(deceased != null)
            holder.deceased.text = Utils.formatNumber(deceased)
    }


    override fun getItemCount(): Int {
        return listItems?.size!!
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linearLayout = itemView.findViewById(R.id.constraintLayout) as LinearLayout
        val bannerText = itemView.findViewById(R.id.home_list_item_name) as TextView
        val confirmed = itemView.findViewById(R.id.confirmed) as TextView
        val active = itemView.findViewById(R.id.active) as TextView
        val recovered = itemView.findViewById(R.id.recovered) as TextView
        val deceased = itemView.findViewById(R.id.deceased) as TextView
    }

    constructor(context: Context, listItems: HashMap<String?, StateData?>?) : this() {
        this.context = context
        this.listItems = listItems
        val keyItem: MutableList<String?> = mutableListOf()
        listItems?.forEach { (key, _) -> keyItem.add(key) }
        this.keyItem = keyItem
    }
}