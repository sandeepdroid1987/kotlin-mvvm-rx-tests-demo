
package com.sandeep.mvvmdemo.feature.converter

import com.sandeep.mvvmdemo.UnitTest
import com.sandeep.mvvmdemo.core.functional.Either.Right
import com.sandeep.mvvmdemo.core.interactor.UseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.sandeep.mvvmdemo.feature.data.repository.CurrencyRepository
import com.sandeep.mvvmdemo.feature.converter.uimodel.Currency
import com.sandeep.mvvmdemo.feature.converter.usecases.GetCurrencyList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetCurrencyListTest : UnitTest() {

    private lateinit var getCurrencyList: GetCurrencyList

    @Mock private lateinit var currencyRepository: CurrencyRepository

    @Before fun setUp() {
        getCurrencyList = GetCurrencyList(currencyRepository)
        given { currencyRepository.currencyList() }.willReturn(Right(listOf(Currency.empty())))
    }

    @Test fun `should get data from repository`() {
        runBlocking { getCurrencyList.run(UseCase.None()) }

        verify(currencyRepository).currencyList()
        verifyNoMoreInteractions(currencyRepository)
    }
}
