package com.sandeep.mvvmdemo.feature.converter.view.activity

import android.content.Context
import android.content.Intent
import com.sandeep.mvvmdemo.core.platform.BaseActivity
import com.sandeep.mvvmdemo.feature.converter.view.fragment.CurrenciesFragment

class CurrencyActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, CurrencyActivity::class.java)
    }

    override fun fragment() = CurrenciesFragment()
}
