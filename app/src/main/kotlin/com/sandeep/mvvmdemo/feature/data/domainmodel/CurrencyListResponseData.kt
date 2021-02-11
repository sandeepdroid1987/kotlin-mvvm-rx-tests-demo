package com.sandeep.mvvmdemo.feature.data.domainmodel

import com.sandeep.mvvmdemo.core.exception.Failure


abstract class ResponseData {
    abstract val error: Failure.ApiError?
    abstract val success: Boolean
}

data class CurrencyListResponseData(val currencies: Map<String, String>?, override val error: Failure.ApiError?, override val success: Boolean) : ResponseData()
