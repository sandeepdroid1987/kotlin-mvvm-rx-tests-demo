
package com.sandeep.mvvmdemo.feature.converter

import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.*
import com.sandeep.mvvmdemo.UnitTest
import com.sandeep.mvvmdemo.feature.data.repository.CurrencyRepository.Network
import com.sandeep.mvvmdemo.core.exception.Failure.NetworkConnection
import com.sandeep.mvvmdemo.core.exception.Failure.ServerError
import com.sandeep.mvvmdemo.core.functional.Either
import com.sandeep.mvvmdemo.core.functional.Either.Right
import com.sandeep.mvvmdemo.core.platform.NetworkHandler
import com.sandeep.mvvmdemo.feature.data.domainmodel.CurrencyListResponseData
import com.sandeep.mvvmdemo.feature.data.remote.impl.CurrencyService
import com.sandeep.mvvmdemo.feature.converter.uimodel.Currency
import com.sandeep.mvvmdemo.feature.converter.uimodel.CurrencyRate
import com.sandeep.mvvmdemo.feature.data.domainmodel.CurrencyLiveResponseData
import com.sandeep.mvvmdemo.feature.data.local.CacheHelper
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class CurrencyRepositoryTest : UnitTest() {

    private lateinit var networkRepository: Network

    @Mock private lateinit var networkHandler: NetworkHandler
    @Mock private lateinit var service: CurrencyService
    @Mock private lateinit var cacheHelper: CacheHelper
    @Mock private lateinit var gson: Gson

    @Mock private lateinit var currencyListCall: Call<CurrencyListResponseData>
    @Mock private lateinit var currencyListResponse: Response<CurrencyListResponseData>
    @Mock private lateinit var currencyLiveCall: Call<CurrencyLiveResponseData>
    @Mock private lateinit var currencyLiveResponse: Response<CurrencyLiveResponseData>

    @Before fun setUp() {
        networkRepository = Network(networkHandler, service, cacheHelper, gson)
    }

    @Test fun `should get currencies from service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { currencyListResponse.body() }.willReturn(CurrencyListResponseData(currencies = mapOf("USD" to "Dollars"), error = null, success = true))
        given { currencyListResponse.isSuccessful }.willReturn(true)
        given { currencyListCall.execute() }.willReturn(currencyListResponse)
        given { service.currencies() }.willReturn(currencyListCall)
        //given { gson.toJson(any()) }.willReturn { "" }
        given { cacheHelper.save(any(), any()) }.willAnswer{}
        given { cacheHelper.retrieve(any()) }.willReturn{ null}

        val currencies = networkRepository.currencyList()

        currencies shouldEqual Right(listOf(Currency("USD" )))
        verify(service).currencies()
    }

    @Test fun `currencies service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val currencies = networkRepository.currencyList()

        currencies shouldBeInstanceOf Either::class.java
        currencies.isLeft shouldEqual true
        currencies.fold({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `currencies service should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val currencies = networkRepository.currencyList()

        currencies shouldBeInstanceOf Either::class.java
        currencies.isLeft shouldEqual true
        currencies.fold({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `currencies service should return server error if no successful response`() {
        given { networkHandler.isConnected }.willReturn(true)

        val currencies = networkRepository.currencyList()

        currencies shouldBeInstanceOf Either::class.java
        currencies.isLeft shouldEqual true
        currencies.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test fun `currencies request should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val currencies = networkRepository.currencyList()

        currencies shouldBeInstanceOf Either::class.java
        currencies.isLeft shouldEqual true
        currencies.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test fun `should get live rates from service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { currencyLiveResponse.body() }.willReturn(
                CurrencyLiveResponseData(quotes = mapOf("USDINR" to 75.0f), error = null, success = true))
        given { currencyLiveResponse.isSuccessful }.willReturn(true)
        given { currencyLiveCall.execute() }.willReturn(currencyLiveResponse)
        given { service.live(any()) }.willReturn(currencyLiveCall)
        given { cacheHelper.retrieve(any()) }.willReturn(null)
        given { cacheHelper.save(any(), any()) }.willAnswer{  }
        val liveRates = networkRepository.currencyLive("")

        liveRates shouldEqual Right(listOf(CurrencyRate("USDINR",  75.0f)))
        verify(service).live("")
    }

    @Test fun `live rates service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val liveRates = networkRepository.currencyLive("")

        liveRates shouldBeInstanceOf Either::class.java
        liveRates.isLeft shouldEqual true
        liveRates.fold({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `live rates service should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val liveRates = networkRepository.currencyLive("")

        liveRates shouldBeInstanceOf Either::class.java
        liveRates.isLeft shouldEqual true
        liveRates.fold({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `live rates service should return server error if no successful response`() {
        given { networkHandler.isConnected }.willReturn(true)

        val liveRates = networkRepository.currencyLive("")

        liveRates shouldBeInstanceOf Either::class.java
        liveRates.isLeft shouldEqual true
        liveRates.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }

    @Test fun `live rates request should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val liveRates = networkRepository.currencyLive("")

        liveRates shouldBeInstanceOf Either::class.java
        liveRates.isLeft shouldEqual true
        liveRates.fold({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }
}