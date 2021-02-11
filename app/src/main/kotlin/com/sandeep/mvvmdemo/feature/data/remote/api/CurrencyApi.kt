
package com.sandeep.mvvmdemo.feature.data.remote.api

import com.sandeep.mvvmdemo.feature.data.domainmodel.CurrencyListResponseData
import com.sandeep.mvvmdemo.feature.data.domainmodel.CurrencyLiveResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CurrencyApi {
    companion object {
        private const val ACCESS_KEY = "84df724ef8edb3106d6e14a16ddf225c"

        private const val LIVE = "live?access_key=$ACCESS_KEY"
        private const val LIST = "list?access_key=$ACCESS_KEY"
    }

    @GET(LIVE) fun live(@Query("source") source: String): Call<CurrencyLiveResponseData>
    @GET(LIST)  fun currencies(): Call<CurrencyListResponseData>
}