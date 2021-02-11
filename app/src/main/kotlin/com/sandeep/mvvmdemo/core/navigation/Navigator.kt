
package com.sandeep.mvvmdemo.core.navigation

import android.content.Context
import com.sandeep.mvvmdemo.feature.converter.view.activity.CurrencyActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor() {

    fun showMain(context: Context) {
        showConverterScreen(context)
    }

    private fun showConverterScreen(context: Context) = context.startActivity(CurrencyActivity.callingIntent(context))

}