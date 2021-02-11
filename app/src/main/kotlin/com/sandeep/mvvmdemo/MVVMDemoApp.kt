
package com.sandeep.mvvmdemo

import android.app.Application
import com.sandeep.mvvmdemo.core.di.ApplicationComponent
import com.sandeep.mvvmdemo.core.di.ApplicationModule
import com.sandeep.mvvmdemo.core.di.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary

class MVVMDemoApp : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        this.initializeLeakDetection()
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}
