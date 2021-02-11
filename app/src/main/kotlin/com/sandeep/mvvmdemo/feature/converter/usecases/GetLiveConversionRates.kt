package com.sandeep.mvvmdemo.feature.converter.usecases

import com.sandeep.mvvmdemo.core.interactor.UseCase
import com.sandeep.mvvmdemo.feature.data.repository.CurrencyRepository
import com.sandeep.mvvmdemo.feature.converter.uimodel.CurrencyRate
import javax.inject.Inject

class GetLiveConversionRates
@Inject constructor(private val currencyRepository: CurrencyRepository) : UseCase<List<CurrencyRate>, UseCase.None>() {
    override suspend fun run(params :None) = currencyRepository.currencyLive()
}
