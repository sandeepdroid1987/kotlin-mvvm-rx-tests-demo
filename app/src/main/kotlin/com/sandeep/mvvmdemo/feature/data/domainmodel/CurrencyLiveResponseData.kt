package com.sandeep.mvvmdemo.feature.data.domainmodel

import com.sandeep.mvvmdemo.core.exception.Failure

data class CurrencyLiveResponseData(val quotes: Map<String, Float>? = null, override val error: Failure.ApiError?, override val success: Boolean) : ResponseData()

