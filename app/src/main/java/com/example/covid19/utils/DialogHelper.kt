package com.example.covid19.utils

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.covid19.bottomsheets.StateDataBS
import com.example.covid19.home.newModel.dataV4.StateData

class DialogHelper {
    private lateinit var context: Context
    private lateinit var checkStateDataDialogListener: CheckStateDataDialogListener

    constructor(context: Context, checkStateDataDialogListener: CheckStateDataDialogListener)
    {
        this.context = context
        this.checkStateDataDialogListener = checkStateDataDialogListener
    }

    constructor()
    {

    }

    fun getHeadingListSlideUp(
        fragmentManager: FragmentManager?,
        districtMap: StateData?,
        stateName: String?
    ) {
        val emailOptionsBS = StateDataBS.newInstance(districtMap, stateName)
        emailOptionsBS.show(
            fragmentManager!!,
            "heading_list"
        )
    }
}