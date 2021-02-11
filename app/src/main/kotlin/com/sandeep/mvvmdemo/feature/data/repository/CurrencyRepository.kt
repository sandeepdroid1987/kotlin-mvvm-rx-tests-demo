
package com.sandeep.mvvmdemo.feature.data.repository

import com.google.gson.Gson
import com.sandeep.mvvmdemo.core.exception.Failure
import com.sandeep.mvvmdemo.core.exception.Failure.NetworkConnection
import com.sandeep.mvvmdemo.core.exception.Failure.ServerError
import com.sandeep.mvvmdemo.core.functional.Either
import com.sandeep.mvvmdemo.core.functional.Either.Left
import com.sandeep.mvvmdemo.core.functional.Either.Right
import com.sandeep.mvvmdemo.core.platform.NetworkHandler
import com.sandeep.mvvmdemo.feature.data.domainmodel.CurrencyListResponseData
import com.sandeep.mvvmdemo.feature.data.domainmodel.CurrencyLiveResponseData
import com.sandeep.mvvmdemo.feature.data.domainmodel.ResponseData
import com.sandeep.mvvmdemo.feature.data.remote.impl.CurrencyService
import com.sandeep.mvvmdemo.feature.converter.uimodel.Currency
import com.sandeep.mvvmdemo.feature.converter.uimodel.CurrencyRate
import com.sandeep.mvvmdemo.feature.converter.utils.Constants.CACHE_FILE_LIST_API_KEY
import com.sandeep.mvvmdemo.feature.converter.utils.Constants.CACHE_FILE_LIVE_API_KEY
import com.sandeep.mvvmdemo.feature.converter.utils.Constants.DEFAULT_CURRENCY
import com.sandeep.mvvmdemo.feature.data.local.CacheHelper
import retrofit2.Call
import javax.inject.Inject

interface CurrencyRepository {
    fun currencyList(): Either<Failure, List<Currency>>
    fun currencyLive(source: String = DEFAULT_CURRENCY): Either<Failure, List<CurrencyRate>>


    class Network
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: CurrencyService,
                        private val cacheHelper: CacheHelper,
                        private val gson: Gson) : CurrencyRepository {

        override fun currencyList(): Either<Failure, List<Currency>> {
          return  cacheHelper.retrieve(CACHE_FILE_LIST_API_KEY)?.let {
                val currencyListResponseData  = gson.fromJson(it, CurrencyListResponseData::class.java)
                Right(currencyListResponseData.currencies?.map { currencyEntity -> Currency(currencyEntity.key)  }?.toList() ?: emptyList())
            } ?: run {
                 when (networkHandler.isConnected) {
                    true -> request(service.currencies(), {
                        (it as CurrencyListResponseData).let {
                            cacheHelper.save(CACHE_FILE_LIST_API_KEY, gson.toJson(it))
                            it.currencies?.map { currencyEntity -> Currency(currencyEntity.key) }
                                ?: emptyList() }
                    }, CurrencyListResponseData(emptyMap(), null, true))
                    false, null -> Left(NetworkConnection)
                }
            }
        }

        override fun currencyLive(source: String): Either<Failure, List<CurrencyRate>> =
                //first check local
            cacheHelper.retrieve(CACHE_FILE_LIVE_API_KEY)?.let {
                val currencyLiveResponseData  = gson.fromJson(it, CurrencyLiveResponseData::class.java)
                  Right(currencyLiveResponseData.quotes?.map { (key, value) -> CurrencyRate(key, value) }?.toList() ?: emptyList())
            } ?: run {
                //then fetch remote
                 when (networkHandler.isConnected) {
                    true -> request(service.live(source), {
                        (it as CurrencyLiveResponseData).let{
                            cacheHelper.save(CACHE_FILE_LIVE_API_KEY, gson.toJson(it))
                            it.quotes?.map { (key, value) -> CurrencyRate(key, value) }
                                ?: emptyList() }
                    }, CurrencyLiveResponseData(null, null, true))
                    false, null -> Left(NetworkConnection)
                }
            }


        private fun <T: ResponseData, R> request(call: Call<T>, transform: (T: ResponseData) -> R, default: T ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> {
                        if(response.body()?.success == true) {
                            Right(transform((response.body() ?: default)))
                        } else if (response.body()?.error != null) {
                            Left(response.body()?.error!!)
                        } else {
                            Left(Failure.UknownError)
                        }

                    }
                    false -> Left(ServerError)
                }
            } catch (exception: Throwable) {
                exception.printStackTrace()
                Left(ServerError)
            }
        }
    }
}
