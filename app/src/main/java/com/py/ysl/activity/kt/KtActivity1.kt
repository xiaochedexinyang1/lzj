package com.py.ysl.activity.kt

import android.os.Bundle
import android.util.Log
import com.py.ysl.R
import com.py.ysl.base.BaseActivity
import java.util.*

class KtActivity1 :BaseActivity() {

    var str :String = "ddd"
    var map : HashMap<String,String> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_view1)
    }

    override fun onResume() {
        super.onResume()
        Log.e("","")
    }
}