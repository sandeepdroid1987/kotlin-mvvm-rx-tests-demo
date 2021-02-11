
package com.sandeep.mvvmdemo.feature.data.remote.impl

import com.sandeep.mvvmdemo.feature.data.remote.api.CurrencyApi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyService
@Inject constructor(retrofit: Retrofit) : CurrencyApi {
    private val currencyApi by lazy { retrofit.create(CurrencyApi::class.java) }
    override fun currencies() = currencyApi.currencies()
    override fun live(source: String) = currencyApi.live(source)

}
