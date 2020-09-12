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
        binding.textState.text = Optional.ofNullable(stateName).orElse("")
        fillJumboTab1(districtMap)
        binding.stateRv.layoutManager =
            LinearLayoutManager(context)
        binding.stateRv.adapter = ItemAdapter(districtMap?.districts?.size!!)
    }

    @SuppressLint("SetTextI18n")
    private fun fillJumboTab1(currentStats: StateData?) {
        Log.v("MAINNN", currentStats?.total?.confirmed.toString())

        binding.homeConfirmed.text = Utils.formatNumber(Optional.ofNullable(currentStats?.total?.confirmed).orElse(0))

        val active = Optional.ofNullable(currentStats?.total?.confirmed).orElse(0).minus(Optional.ofNullable(currentStats?.total?.recovered).orElse(0)).minus(
            Optional.ofNullable(currentStats?.total?.deceased).orElse(0))
        binding.homeActive.text = Utils.formatNumber(active)

        binding.homeRecovered.text = Utils.formatNumber(Optional.ofNullable(currentStats?.total?.recovered).orElse(0))
        binding.homeDeceased.text = Utils.formatNumber(Optional.ofNullable(currentStats?.total?.deceased).orElse(0))

        val confirmedDelta = Utils.formatNumber(Optional.ofNullable(currentStats?.delta?.confirmed).orElse(0))
        binding.homeConfirmedDelta.text = "[+$confirmedDelta]"

        val deceasedDelta = Utils.formatNumber(Optional.ofNullable(currentStats?.delta?.deceased).orElse(0))
        binding.homeDeceasedDelta.text = "[+$deceasedDelta]"

        val recoveredDelta = Utils.formatNumber(Optional.ofNullable(currentStats?.delta?.recovered).orElse(0))
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

                holder.bannerText.text = context!!.resources.getString(R.string.district)
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

            val confirmed = districtMap?.districts?.get(keyItem?.get(position)!!)?.total?.confirmed
            val recovered = districtMap?.districts?.get(keyItem?.get(position)!!)?.total?.recovered
            val  deceased = districtMap?.districts?.get(keyItem?.get(position)!!)?.total?.deceased

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
        var districtMap: StateData? = null
        var keyItem: ArrayList<String?>? = null
        var stateName: String? = null
        fun newInstance(
            districtMap: StateData?,
            stateName: String?
        ): StateDataBS {
            this.stateName = stateName
            this.districtMap = districtMap
            this.keyItem = arrayListOf()
            districtMap?.districts?.forEach { (key, _) -> this.keyItem?.add(key) }
            return StateDataBS()
        }
    }
}