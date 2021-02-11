
package com.sandeep.mvvmdemo.core.di

import com.sandeep.mvvmdemo.MVVMDemoApp
import com.sandeep.mvvmdemo.core.di.viewmodel.ViewModelModule
import com.sandeep.mvvmdemo.feature.converter.view.fragment.CurrenciesFragment
import com.sandeep.mvvmdemo.core.navigation.RouteActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: MVVMDemoApp)
    fun inject(routeActivity: RouteActivity)

    fun inject(currencyFragment: CurrenciesFragment)
}
