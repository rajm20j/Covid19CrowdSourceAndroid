package com.example.covid19.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.example.covid19.MyApp
import com.example.covid19.R
import com.example.covid19.data.model.ApiResponse
import com.example.covid19.data.model.Status
import com.example.covid19.databinding.ActivityHomeBinding
import com.example.covid19.extras.Constants
import com.example.covid19.home.model.CasesTimeSery
import com.example.covid19.home.model.KeyValue
import com.example.covid19.home.newModel.dataV4.StateData
import com.example.covid19.home.newModel.timeSeries.Dates
import com.example.covid19.utils.Utils
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.DecimalFormat
import javax.inject.Inject

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

        supportActionBar?.hide()

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

    private fun consumeHomeData(apiResponse: ApiResponse) {
        when (apiResponse.status) {
            Status.LOADING -> {
            }

            Status.ERROR -> {
                renderErrorHomeDataResponse(apiResponse.error)
            }

            Status.SUCCESS -> {
                renderSuccessHomeDataResponse(apiResponse.data)
            }
            else -> Log.e(TAG, "Ye kya hua? :O")
        }
    }

    private fun renderSuccessHomeDataResponse(data: JsonElement?) {
        val jsonObject = data!!.asJsonObject
        Utils.logInPrettyFormat(TAG, jsonObject.toString())

        var allData: HashMap<String?, StateData?>? = null
        try {
            val type: Type = object : TypeToken<HashMap<String?, StateData?>?>() {}.type
            allData = gson.fromJson(jsonObject.toString(), type)
            Log.v("MAINNN", "Data kiya store")
        } catch (e: Exception) {
            Toast.makeText(this, "Arrey aggey ni parse ho ra", Toast.LENGTH_SHORT).show()
        }
        Log.v("MAINNN", allData!!.size.toString())
        for ((key, value) in allData)
        {
            Log.v("MAINNN", "$key: ${value?.total?.confirmed}")
        }
//        binding.homeRvForTopTab.adapter = HomeStatsListAdapter(this, allData!!.statewise)
//        binding.homeRvForTopTab.layoutManager= LinearLayoutManager(this)

        fillJumboTab(allData)
//        generateChart(allData?.cases_time_series)
//        generateChart2(allData?.cases_time_series)
    }

    private fun consumeHomeStateData(apiResponse: ApiResponse) {
        when (apiResponse.status) {
            Status.LOADING -> {
            }

            Status.ERROR -> {
                renderErrorHomeSDataResponse(apiResponse.error)
            }

            Status.SUCCESS -> {
                renderSuccessHomeSDataResponse(apiResponse.data)
            }
            else -> Log.e(TAG, "Ye kya hua? :O")
        }
    }

    private fun renderSuccessHomeSDataResponse(data: JsonElement?) {
        val jsonObject = data!!.asJsonObject
        Utils.logInPrettyFormat(TAG, jsonObject.toString())

        var allData: HashMap<String?, Dates?>? = null
        try {
            val type: Type = object : TypeToken<HashMap<String?, Dates?>?>() {}.type
            allData = gson.fromJson(jsonObject.toString(), type)
            Log.v("MAINNN", "Data kiya store")
        } catch (e: Exception) {
            Toast.makeText(this, "Arrey aggey ni parse ho ra", Toast.LENGTH_SHORT).show()
        }
        Log.v("MAINNN", allData!!.size.toString())
        for ((key, value) in allData)
        {
            Log.v("MAINNN", "$key == ${value?.dates?.get("2020-09-10")?.total?.confirmed.toString()}")
        }
//        binding.homeRvForTopTab.adapter = HomeStatsListAdapter(this, allData!!.statewise)
//        binding.homeRvForTopTab.layoutManager= LinearLayoutManager(this)

//        fillJumboTab(allData)
//        generateChart(allData?.cases_time_series)
//        generateChart2(allData?.cases_time_series)
    }

   /* private fun generateChart2(casesTimeSeries: List<CasesTimeSery>?) {

        binding.homeChart2.setDrawGridBackground(false)
        binding.homeChart2.description.isEnabled = false
        binding.homeChart2.setDrawBorders(false)

        binding.homeChart2.axisLeft.isEnabled = false
        binding.homeChart2.axisRight.setDrawAxisLine(false)
        binding.homeChart2.axisRight.setDrawGridLines(false)
        binding.homeChart2.xAxis.setDrawAxisLine(false)

        // enable touch gestures

        // enable touch gestures
        binding.homeChart2.setTouchEnabled(true)

        // enable scaling and dragging

        // enable scaling and dragging
        binding.homeChart2.isDragEnabled = true
        binding.homeChart2.setScaleEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately

        // if disabled, scaling can be done on x- and y-axis separately
        binding.homeChart2.setPinchZoom(false)

        val l: Legend = binding.homeChart2.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)

        val dataSets = ArrayList<ILineDataSet>()

        val values = ArrayList<Entry>()

        for ((i, item) in casesTimeSeries!!.withIndex()) {
            if (item.totalconfirmed != "") {
                values.add(Entry(i.toFloat(), item.totalconfirmed.toFloat()))
            }
        }
        val d = LineDataSet(values, "DataSet " + (1))
        d.lineWidth = 1.0f

        var color = resources.getColor(R.color.confirmed_dark)
        d.color = color
        d.setCircleColor(color)
        dataSets.add(d)


        // make the first DataSet dashed
        (dataSets[0] as LineDataSet).enableDashedLine(10f, 10f, 0f)
        (dataSets[0] as LineDataSet).setColors(*ColorTemplate.VORDIPLOM_COLORS)
        (dataSets[0] as LineDataSet).setCircleColors(*ColorTemplate.VORDIPLOM_COLORS)

        val data = LineData(dataSets)
        binding.homeChart2.data = data
        binding.homeChart2.invalidate()

    }*/

    @SuppressLint("SetTextI18n")
    private fun fillJumboTab(currentStats: HashMap<String?, StateData?>?) {

        var total = 0
        currentStats?.forEach { (_, value) -> total += value?.total?.confirmed!! }

        binding.homeConfirmed.text = Utils.formatNumber(currentStats?.get(Constants.India)?.total?.confirmed)

        val active = currentStats?.get(Constants.India)?.total?.confirmed!!.minus(currentStats[Constants.India]?.total?.recovered!!).minus(
            currentStats[Constants.India]?.total?.deceased!!)
        binding.homeActive.text = Utils.formatNumber(active)
        binding.homeRecovered.text = Utils.formatNumber(currentStats[Constants.India]?.total?.recovered)
        binding.homeDeceased.text = Utils.formatNumber(currentStats[Constants.India]?.total?.deceased)
////
////        val confirmedDelta = deltas!!.confirmeddelta
////        val deceasedDelta = deltas.deceaseddelta
////        val recoveredDelta = deltas.recovereddelta
//        val activeDelta = confirmedDelta.toInt() - deceasedDelta.toInt() - recoveredDelta.toInt()
//        binding.homeConfirmedDelta.text = "[+$confirmedDelta]"
//        binding.homeDeceasedDelta.text = "[+$deceasedDelta]"
//        binding.homeRecoveredDelta.text = "[+$recoveredDelta]"
//        binding.homeActiveDelta.text = "[+$activeDelta]"

    }

    /*private fun generateChart(casesTimeSeries: List<CasesTimeSery>?) {
        binding.homeChart.setProgressBar(binding.homePb)

        val cartesian = AnyChart.line()
        cartesian.animation(true)
        cartesian.padding(10.0, 20.0, 5.0, 20.0)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
            .yLabel(true) // TODO ystroke
            .yStroke(
                null as Stroke?,
                null,
                null,
                null as String?,
                null as String?
            )

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.yAxis(0).title("Affected patients")
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        val seriesData: MutableList<DataEntry> = ArrayList()
        for (item in casesTimeSeries!!) {
            var date: String? = null
            var totalconfirmed: String? = null
            var totaldeceased: String? = null
            var totalrecovered: String? = null

            if (item.date != "")
                date = item.date

            if (item.totalconfirmed != "")
                totalconfirmed = item.totalconfirmed

            if (item.totaldeceased != "")
                totaldeceased = item.totaldeceased

            if (item.totalrecovered != "")
                totalrecovered = item.totalrecovered

            seriesData.add(
                CustomDataEntry(
                    date,
                    totalconfirmed?.toInt(),
                    totaldeceased?.toInt(),
                    totalrecovered?.toInt()
                )
            )
        }

        val set = Set.instantiate()
        set.data(seriesData)
        val series1Mapping = set.mapAs("{ x: 'x', value: 'value' }")
        val series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }")
        val series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }")

        val series1 = cartesian.line(series1Mapping)
        series1.name("Total Confirmed")
            .color(resources.getString(R.string.confirmed_dark))
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        val series2 = cartesian.line(series2Mapping)
        series2.name("Total Deceased")
            .color(resources.getString(R.string.deceased_dark))
        series2.hovered().markers().enabled(true)
        series2.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series2.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        val series3 = cartesian.line(series3Mapping)
        series3.name("Total Recovered")
            .color(resources.getString(R.string.recovered_dark))
        series3.hovered().markers().enabled(true)
        series3.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series3.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.background("#fafafa")
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

        binding.homeChart.setChart(cartesian)
    }*/


    class CustomDataEntry(
        x: String?,
        value: Number?,
        value2: Number?,
        value3: Number?
    ) :
        ValueDataEntry(x, value) {
        init {
            setValue("value2", value2)
            setValue("value3", value3)
        }
    }

    private fun renderErrorHomeDataResponse(error: Throwable?) {
        Toast.makeText(this, "Error loading data", Toast.LENGTH_LONG).show()
    }

    private fun renderErrorHomeSDataResponse(error: Throwable?) {
        Toast.makeText(this, "Error loading data", Toast.LENGTH_LONG).show()
    }
}
