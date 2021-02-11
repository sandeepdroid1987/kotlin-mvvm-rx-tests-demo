
package com.sandeep.mvvmdemo.core.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sandeep.mvvmdemo.MVVMDemoApp
import com.sandeep.mvvmdemo.core.di.ApplicationComponent
import javax.inject.Inject

class RouteActivity : AppCompatActivity() {

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as MVVMDemoApp).appComponent
    }

    @Inject internal lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        navigator.showMain(this)
    }
}
