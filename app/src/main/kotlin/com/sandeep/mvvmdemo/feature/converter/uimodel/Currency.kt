
package com.sandeep.mvvmdemo.feature.converter.uimodel

import com.sandeep.mvvmdemo.core.extension.empty

data class Currency(val name: String) {

    companion object {
        fun empty() = Currency(String.empty())
    }
}
