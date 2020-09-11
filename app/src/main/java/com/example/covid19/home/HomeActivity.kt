package com.example.covid19.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19.MyApp
import com.example.covid19.R
import com.example.covid19.data.model.ApiResponse
import com.example.covid19.data.model.Status
import com.example.covid19.databinding.ActivityHomeBinding
import com.example.covid19.home.model.HomeStatsListAdapter
import com.example.covid19.home.newModel.dataV4.StateData
import com.example.covid19.home.newModel.timeSeries.DateData
import com.example.covid19.home.newModel.timeSeries.DateInfo
import com.example.covid19.utils.Utils
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap


class HomeActivity : AppCompatActivity() {

    val TAG = this.javaClass.simpleName

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeActivityViewModel: HomeActivityViewModel

    @Inject
    lateinit var homeActivityViewModelFactory: HomeActivityViewModelFactory

    @Inject
    lateinit var gson: Gson



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        (application as MyApp).myComponent.doInjection(this)

        homeActivityViewModel = ViewModelProvider(
            this,
            homeActivityViewModelFactory
        ).get(HomeActivityViewModel::class.java)
        homeActivityViewModel.homeDataResponse.observe(
            this,
            Observer<ApiResponse> { this.consumeHomeData(it) })

        homeActivityViewModel.homeStateDataResponse.observe(
            this,
            Observer<ApiResponse> { this.consumeHomeStateData(it) })

        homeActivityViewModel.hitHomeDataApi()
        homeActivityViewModel.hitHomeStateDataApi()
    }

    private fun setActionBarLayout() {
        binding.appBar.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    Toast.makeText(this@HomeActivity, "Collapse", Toast.LENGTH_SHORT).show()
                    Log.v("MAINNN", "Collapse")
                    isShow = true
                    binding.collapsingTitle.visibility = View.GONE
                } else if (isShow) {
                    Toast.makeText(this@HomeActivity, "Expand", Toast.LENGTH_SHORT).show()
                    Log.v("MAINNN", "Expand")
                    isShow = false
                    binding.collapsingTitle.title = resources.getString(R.string.india_covid_19_tracker)
                    binding.collapsingTitle.isTitleEnabled = true
                    binding.collapsingTitle.setExpandedTitleColor(resources.getColor(android.R.color.white, null))
                }
            }
        })
    }

    private fun consumeHomeData(apiResponse: ApiResponse) {
        when (apiResponse.status) {
            Status.LOADING -> {
                binding.cardShimmer.startShimmer()
            }

            Status.ERROR -> {
                binding.cardShimmer.stopShimmer()
                renderErrorHomeDataResponse(apiResponse.error)
            }

            Status.SUCCESS -> {
                renderSuccessHomeDataResponse(apiResponse.data)
                binding.cardShimmer.stopShimmer()
                binding.cardShimmer.hideShimmer()
            }
            else -> Log.e(TAG, "Ye kya hua? :O")
        }
    }

    private fun renderSuccessHomeDataResponse(data: JsonElement?) {
        val jsonObject = data!!.asJsonObject
        Utils.logInPrettyFormat(TAG, jsonObject.toString())

        var allData: SortedMap<String?, StateData?>? = null
        try {
            val type: Type = object : TypeToken<SortedMap<String?, StateData?>?>() {}.type
            allData = gson.fromJson(jsonObject.toString(), type)
        } catch (e: Exception) {
            Toast.makeText(this, "Arrey aggey ni parse ho ra", Toast.LENGTH_SHORT).show()
        }

        binding.homeRv.adapter = HomeStatsListAdapter(this, allData, supportFragmentManager)
        binding.homeRv.layoutManager= LinearLayoutManager(this)
        (binding.homeRv.adapter as HomeStatsListAdapter).notifyItemRangeInserted(0, allData!!.size)
    }

    private fun consumeHomeStateData(apiResponse: ApiResponse) {
        when (apiResponse.status) {
            Status.LOADING -> {
                binding.jumboShimmer.startShimmer()
            }

            Status.ERROR -> {
                renderErrorHomeSDataResponse(apiResponse.error)
                binding.jumboShimmer.stopShimmer()
            }

            Status.SUCCESS -> {
                renderSuccessHomeSDataResponse(apiResponse.data)
                binding.jumboShimmer.stopShimmer()
                binding.jumboShimmer.hideShimmer()
            }
            else -> Log.e(TAG, "Ye kya hua? :O")
        }
    }

    private fun renderSuccessHomeSDataResponse(data: JsonElement?) {
        val jsonObject = data!!.asJsonObject
        Utils.logInPrettyFormat(TAG, jsonObject.toString())

        var allData: HashMap<String?, DateData?>? = null
        try {
            val type: Type = object : TypeToken<HashMap<String?, DateData?>?>() {}.type
            allData = gson.fromJson(jsonObject.toString(), type)
        } catch (e: Exception) {
            Toast.makeText(this, "Arrey aggey ni parse ho ra", Toast.LENGTH_SHORT).show()
        }
        val lastDateEntry  = allData?.get("TT")?.dates?.lastEntry()?.key!!
        fillJumboTab(allData["TT"]?.dates?.get(lastDateEntry))
    }

    @SuppressLint("SetTextI18n")
    private fun fillJumboTab(currentStats: DateInfo?) {

        var total = 0

        binding.homeConfirmed.text = Utils.formatNumber(currentStats?.total?.confirmed)

        val active = currentStats?.total?.confirmed!!.minus(currentStats.total?.recovered!!).minus(
            currentStats.total?.deceased!!)
        binding.homeActive.text = Utils.formatNumber(active)
        binding.homeRecovered.text = Utils.formatNumber(currentStats.total?.recovered)
        binding.homeDeceased.text = Utils.formatNumber(currentStats.total?.deceased)

        val confirmedDelta = Utils.formatNumber(currentStats.delta?.confirmed)
        binding.homeConfirmedDelta.text = "[+$confirmedDelta]"

        val deceasedDelta = Utils.formatNumber(currentStats.delta?.deceased)
        binding.homeDeceasedDelta.text = "[+$deceasedDelta]"

        val recoveredDelta = Utils.formatNumber(currentStats.delta?.recovered)
        binding.homeRecoveredDelta.text = "[+$recoveredDelta]"

        val activeDelta = Utils.formatNumber(currentStats.delta?.confirmed!!.minus(currentStats.delta?.recovered!!).minus(
            currentStats.delta?.deceased!!))
        binding.homeActiveDelta.text = "[+$activeDelta]"
    }

    private fun renderErrorHomeDataResponse(error: Throwable?) {
        Toast.makeText(this, "Error loading data", Toast.LENGTH_LONG).show()
    }

    private fun renderErrorHomeSDataResponse(error: Throwable?) {
        Toast.makeText(this, "Error loading data", Toast.LENGTH_LONG).show()
    }
}
