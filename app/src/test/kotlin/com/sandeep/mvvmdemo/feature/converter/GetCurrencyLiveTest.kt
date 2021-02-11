
package com.sandeep.mvvmdemo.feature.converter

import com.nhaarman.mockitokotlin2.any
import com.sandeep.mvvmdemo.UnitTest
import com.sandeep.mvvmdemo.core.functional.Either.Right
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sandeep.mvvmdemo.core.interactor.UseCase
import com.sandeep.mvvmdemo.feature.data.repository.CurrencyRepository
import com.sandeep.mvvmdemo.feature.converter.uimodel.CurrencyRate
import com.sandeep.mvvmdemo.feature.converter.usecases.GetLiveConversionRates
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetCurrencyLiveTest : UnitTest() {


    private lateinit var getLiveConversionRates: GetLiveConversionRates

    @Mock private lateinit var currencyRepository: CurrencyRepository

    @Before fun setUp() {
        getLiveConversionRates = GetLiveConversionRates(currencyRepository)
        given { currencyRepository.currencyLive(any()) }.willReturn(Right(listOf(CurrencyRate.empty())))
    }

    @Test fun `should get data from repository`() {
        runBlocking { getLiveConversionRates.run(UseCase.None()) }

        verify(currencyRepository).currencyLive(any())
        verifyNoMoreInteractions(currencyRepository)
    }
}
