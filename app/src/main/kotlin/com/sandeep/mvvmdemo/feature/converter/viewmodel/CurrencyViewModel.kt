package com.sandeep.mvvmdemo.feature.converter.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sandeep.mvvmdemo.core.exception.Failure
import com.sandeep.mvvmdemo.core.interactor.UseCase.None
import com.sandeep.mvvmdemo.core.platform.BaseViewModel
import com.sandeep.mvvmdemo.feature.converter.usecases.GetCurrencyList
import com.sandeep.mvvmdemo.feature.converter.uimodel.Currency
import com.sandeep.mvvmdemo.feature.converter.uimodel.CurrencyRate
import com.sandeep.mvvmdemo.feature.converter.usecases.GetLiveConversionRates
import com.sandeep.mvvmdemo.feature.converter.uimodel.ConvertedCurrency
import com.sandeep.mvvmdemo.feature.converter.utils.Constants.DEFAULT_CURRENCY
import com.sandeep.mvvmdemo.feature.converter.view.fragment.UiState
import java.lang.NumberFormatException
import javax.inject.Inject

class CurrencyViewModel
@Inject constructor(private val getCurrencyList: GetCurrencyList, private val getLiveConversionRates: GetLiveConversionRates) : BaseViewModel() {

    var currencies: MutableLiveData<List<Currency>> = MutableLiveData()
    var conversions: MutableLiveData<List<ConvertedCurrency>> = MutableLiveData()
    var uiState : MutableLiveData<UiState> = MutableLiveData()
    private var mapUSDToOthers: Map<String, Float>? = null
    private var enteredAmount: Int = 0
    private var selectedCurrencyPosition : Int = 0


    fun loadCurrencies()  {
        uiState.postValue(UiState.ShowProgress)
        getCurrencyList(None()) {
            it.fold(::onError, ::handleCurrencyList)
        }
    }

    private fun handleCurrencyList(currencies: List<Currency>) {
        uiState.postValue(UiState.HideProgress)
        this.currencies.value = currencies
        if (mapUSDToOthers == null) {
            getLiveConversionRates(None()) {
                it.fold(::onError, ::saveUSDCurrencyRate)
            }
        }
    }

    private fun onError(failure: Failure) {
        uiState.postValue(UiState.HideProgress)
        handleFailure(failure)
    }

     fun onEnterAmountChange(text: String) {
        try {
            enteredAmount = Integer.parseInt(text)
            computeConversions()
        } catch (e:NumberFormatException) {
            e.printStackTrace()
            uiState.postValue(UiState.InvalidAmount)
        }
    }

     fun onBaseCurrencyChange(position: Int) {
         selectedCurrencyPosition = position
         computeConversions()
    }

    private fun computeConversions() {
        if(enteredAmount > 0) {
            mapUSDToOthers?.let {
                handleCurrencyRates(enteredAmount, it, selectedCurrencyPosition)
            }
        } else {
            uiState.postValue(UiState.InvalidAmount)
        }
    }

    private fun saveUSDCurrencyRate(list: List<CurrencyRate>) {
        mapUSDToOthers = list.map { cur -> cur.name to cur.value }.toMap()
    }

    private fun handleCurrencyRates(enteredValue: Int, liveRatesMap: Map<String, Float>, position: Int) {
        val chosenCurrency = currencies.value!!.get(position)
        val chosenCurrencyKey = DEFAULT_CURRENCY + chosenCurrency.name
        val usdToChosenCurrency = liveRatesMap.get(chosenCurrencyKey) ?: 1.0f

        this.conversions.value = liveRatesMap.filterNot { it.key == chosenCurrencyKey }.map { liveRate ->

            run {
                val rate = liveRate.value / usdToChosenCurrency
                ConvertedCurrency(currencyType = liveRate.key.substring(3),
                        rate = rate,
                        convertedValue = enteredValue.let {
                            "%.2f".format(it.toFloat() * rate).toBigDecimal()
                        })
            }
        }
    }
}