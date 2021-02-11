package com.sandeep.mvvmdemo.feature.converter.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sandeep.mvvmdemo.core.platform.BaseFragment
import com.sandeep.mvvmdemo.R
import com.sandeep.mvvmdemo.core.exception.Failure
import com.sandeep.mvvmdemo.core.exception.Failure.NetworkConnection
import com.sandeep.mvvmdemo.core.exception.Failure.ServerError
import com.sandeep.mvvmdemo.core.extension.*
import com.sandeep.mvvmdemo.core.navigation.Navigator
import com.sandeep.mvvmdemo.feature.converter.uimodel.Currency
import com.sandeep.mvvmdemo.feature.converter.uimodel.ConvertedCurrency
import com.sandeep.mvvmdemo.feature.converter.view.adapter.CurrenciesAdapter
import com.sandeep.mvvmdemo.feature.converter.viewmodel.CurrencyViewModel
import kotlinx.android.synthetic.main.fragment_currencies.*
import javax.inject.Inject

class CurrenciesFragment : BaseFragment(), AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var currenciesAdapter: CurrenciesAdapter

    private lateinit var currencyViewModel: CurrencyViewModel

    override fun layoutId() = R.layout.fragment_currencies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        currencyViewModel = viewModel(viewModelFactory) {
            observe(currencies, ::renderCurrencyList)
            observe(conversions, ::renderConversionList)
            observe(uiState, ::handleUiState)
            failure(failure, ::handleFailure)
        }
    }

    private fun handleUiState(state: UiState?) {
        when(state) {
            is UiState.HideProgress ->  {progress.invisible()}
            is UiState.ShowProgress -> {progress.visible()}
            is UiState.InvalidAmount -> {
                Toast.makeText(context, "Enter a valid amount", Toast.LENGTH_LONG).show()}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadBaseCurrencies()
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        // use position to know the selected item
        currencyViewModel.onBaseCurrencyChange(position)
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {
        //No-implementation
    }

    private fun initializeView() {
        et_enter_amount.addTextChangedListener( object:TextWatcher {
            override fun afterTextChanged( editable: Editable) {
                currencyViewModel.onEnterAmountChange(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //No-implementation
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //No-implementation
            }

        })
        currencyList.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        currencyList.adapter = currenciesAdapter
    }

    private fun loadBaseCurrencies() {
        emptyView.invisible()
        currencyList.visible()
        currencyViewModel.loadCurrencies()
    }

    private fun renderCurrencyList(currencies: List<Currency>?) {

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val currencyList = currencies?.map { currency -> currency.name }?.toList()

        currencyList?.let {
            val arrayAdapter = ArrayAdapter(context as Context, android.R.layout.simple_spinner_item, it)
            // Set layout to use when the list of choices appear
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            currency_chooser.run {
                setOnItemSelectedListener(this@CurrenciesFragment)
                setAdapter(arrayAdapter)
            }
        }

    }

    private fun renderConversionList(convertedCurrencies: List<ConvertedCurrency>?) {
        currenciesAdapter.collection = convertedCurrencies.orEmpty()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        currencyList.invisible()
        emptyView.visible()
        notifyWithAction(message, R.string.action_refresh, ::loadBaseCurrencies)
    }
}

sealed class UiState {
    object ShowProgress : UiState()
    object HideProgress : UiState()
    object InvalidAmount: UiState()
}

