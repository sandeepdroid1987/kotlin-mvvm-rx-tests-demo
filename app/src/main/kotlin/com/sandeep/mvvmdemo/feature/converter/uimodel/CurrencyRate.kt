
package com.sandeep.mvvmdemo.feature.converter.uimodel

import com.sandeep.mvvmdemo.core.extension.empty


data class CurrencyRate(val name: String, val value: Float) {

    companion object {
        fun empty() = CurrencyRate(String.empty(), 0.0f)
    }
}





