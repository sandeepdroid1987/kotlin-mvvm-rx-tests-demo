
package com.sandeep.mvvmdemo.feature.converter

import com.sandeep.mvvmdemo.AndroidTest
import com.sandeep.mvvmdemo.core.functional.Either.Right
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.sandeep.mvvmdemo.feature.converter.uimodel.Currency
import com.sandeep.mvvmdemo.feature.converter.uimodel.CurrencyRate
import com.sandeep.mvvmdemo.feature.converter.viewmodel.CurrencyViewModel
import com.sandeep.mvvmdemo.feature.converter.usecases.GetCurrencyList
import com.sandeep.mvvmdemo.feature.converter.usecases.GetLiveConversionRates
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class CurrencyViewModelTest : AndroidTest() {


    private lateinit var currencyViewModel: CurrencyViewModel

    @Mock private lateinit var getCurrencyList: GetCurrencyList
    @Mock private lateinit var getLiveConversionRates: GetLiveConversionRates

    @Before
    fun setUp() {
        currencyViewModel = CurrencyViewModel(getCurrencyList, getLiveConversionRates)
    }

    @Test fun `loading currency list should update list livedata`() {
        val currencyList = listOf(Currency("USD"))
        given { runBlocking { getCurrencyList.run(any()) } }.willReturn(Right(currencyList))

        currencyViewModel.currencies.observeForever {
            with(it.first()) {
                name shouldEqualTo "USD"
            }
        }

        runBlocking { currencyViewModel.loadCurrencies() }
    }


    @Test fun `computing value should update conversion livedata`() {
        val ratesList = listOf(CurrencyRate("USD", 100.0f)).toMutableList()
        given { runBlocking { getLiveConversionRates.run(any()) } }.willReturn(Right(ratesList))

        currencyViewModel.conversions.observeForever {
            with(it.first()) {
                currencyType shouldEqualTo "USD"
                convertedValue.toString() shouldEqualTo "10000.00"
            }
        }

        runBlocking {
            currencyViewModel.onEnterAmountChange( "100")
            currencyViewModel.onBaseCurrencyChange(0) }
    }
}