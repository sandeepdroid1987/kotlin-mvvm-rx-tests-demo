package com.sandeep.mvvmdemo.feature.converter.uimodel

import java.math.BigDecimal

data class ConvertedCurrency(val currencyType: String, val convertedValue: BigDecimal, val rate: Float)
