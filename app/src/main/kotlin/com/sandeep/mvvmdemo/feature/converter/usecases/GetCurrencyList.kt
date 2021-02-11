package com.sandeep.mvvmdemo.feature.converter.usecases

import com.sandeep.mvvmdemo.core.interactor.UseCase
import com.sandeep.mvvmdemo.core.interactor.UseCase.None
import com.sandeep.mvvmdemo.feature.data.repository.CurrencyRepository
import com.sandeep.mvvmdemo.feature.converter.uimodel.Currency
import javax.inject.Inject

class GetCurrencyList
@Inject constructor(private val currencyRepository: CurrencyRepository) : UseCase<List<Currency>, None>() {

    override suspend fun run(params: None) = currencyRepository.currencyList()
}
