package com.example.covid19.bottomsheets

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19.R
import com.example.covid19.databinding.FragmentStateDataBsListBinding
import com.example.covid19.databinding.StateDataListBinding
import com.example.covid19.home.newModel.dataV4.StateData
import com.example.covid19.home.newModel.dataV4.districts.Districts
import com.example.covid19.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class StateDataBS : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentStateDataBsListBinding
    private lateinit var listBinding: StateDataListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStateDataBsListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fillJumboTab1()
        binding.stateRv.layoutManager =
            LinearLayoutManager(context)
        binding.stateRv.adapter = ItemAdapter(districtMap?.size!!)
    }

    @SuppressLint("SetTextI18n")
    private fun fillJumboTab1(currentStats: StateData?) {
        Log.v("MAINNN", currentStats?.total?.confirmed.toString())
        binding.homeConfirmed.text = Utils.formatNumber(currentStats?.total?.confirmed)

        val active = currentStats?.total?.confirmed!!.minus(currentStats.total.recovered!!).minus(
            currentStats.total.deceased!!)
        binding.homeActive.text = Utils.formatNumber(active)
        binding.homeRecovered.text = Utils.formatNumber(currentStats.total.recovered)
        binding.homeDeceased.text = Utils.formatNumber(currentStats.total.deceased)

        val confirmedDelta = Utils.formatNumber(currentStats.delta?.confirmed)
        binding.homeConfirmedDelta.text = "[+$confirmedDelta]"

        val deceasedDelta = Utils.formatNumber(currentStats.delta?.deceased)
        binding.homeDeceasedDelta.text = "[+$deceasedDelta]"

        val recoveredDelta = Utils.formatNumber(currentStats.delta?.recovered)
        binding.homeRecoveredDelta.text = "[+$recoveredDelta]"

        /*val activeDelta = Utils.formatNumber(currentStats.delta?.confirmed!!.minus(currentStats.delta?.recovered!!).minus(
            currentStats.delta?.deceased!!))
        binding.homeActiveDelta.text = "[+$activeDelta]"*/
        binding.homeActiveDelta.visibility = View.INVISIBLE
    }

    private inner class ViewHolder internal constructor(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) :
        RecyclerView.ViewHolder(
            inflater.inflate(
                R.layout.state_data_list,
                parent,
                false
            )
        ) {
        val linearLayout = itemView.findViewById(R.id.constraintLayout) as LinearLayout
        val bannerText = itemView.findViewById(R.id.home_list_item_name) as TextView
        val confirmed = itemView.findViewById(R.id.confirmed) as TextView
        val active = itemView.findViewById(R.id.active) as TextView
        val recovered = itemView.findViewById(R.id.recovered) as TextView
        val deceased = itemView.findViewById(R.id.deceased) as TextView
    }

    private inner class ItemAdapter internal constructor(private val mItemCount: Int) :
        RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if(position%2 == 0)
                holder.linearLayout.setBackgroundColor(context?.resources?.getColor(R.color.almost_almost_white, null)!!)
            holder.bannerText.text = keyItem?.get(position)!!
            holder.bannerText.setBackgroundColor(context?.resources?.getColor(R.color.almost_almost_white, null)!!)

            if(position == 0) {
                holder.linearLayout.setBackgroundColor(context?.resources!!.getColor(R.color.almost_black, null))
                holder.bannerText.setBackgroundColor(context!!.resources.getColor(R.color.almost_black, null))

                holder.bannerText.text = context!!.resources.getString(R.string.stateut)
                holder.bannerText.setTextColor(context!!.resources.getColor(R.color.almost_white, null))

                holder.confirmed.text = context!!.resources.getString(R.string.confirmed)
                holder.confirmed.setTextColor(context!!.resources.getColor(R.color.almost_white, null))

                holder.active.text = context!!.resources.getString(R.string.active)
                holder.active.setTextColor(context!!.resources.getColor(R.color.almost_white, null))

                holder.recovered.text = context!!.resources.getString(R.string.recovered)
                holder.recovered.setTextColor(context!!.resources.getColor(R.color.almost_white, null))

                holder.deceased.text = context!!.resources.getString(R.string.deceased)
                holder.deceased.setTextColor(context!!.resources.getColor(R.color.almost_white, null))

                return
            }

            val confirmed = districtMap?.get(keyItem?.get(position)!!)?.total?.confirmed
            val recovered = districtMap?.get(keyItem?.get(position)!!)?.total?.recovered
            val  deceased = districtMap?.get(keyItem?.get(position)!!)?.total?.deceased

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
            return mItemCount
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }
    }

    companion object {
        var districtMap: SortedMap<String, Districts>? = null
        var keyItem: ArrayList<String?>? = null
        fun newInstance(districtMap: SortedMap<String, Districts>?): StateDataBS {
            this.districtMap = districtMap
            this.keyItem = arrayListOf()
            districtMap?.forEach { (key, _) -> this.keyItem?.add(key) }
            return StateDataBS()
        }
    }
}