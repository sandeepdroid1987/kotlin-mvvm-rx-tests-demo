
package com.sandeep.mvvmdemo.core.navigation

import com.sandeep.mvvmdemo.AndroidTest
import com.sandeep.mvvmdemo.feature.converter.view.activity.CurrencyActivity
import com.sandeep.mvvmdemo.shouldNavigateTo
import org.junit.Before
import org.junit.Test


class NavigatorTest : AndroidTest() {

    private lateinit var navigator: Navigator

    @Before fun setup() {
        navigator = Navigator()
    }

    @Test fun `should forward user to currencies screen`() {
        navigator.showMain(activityContext())
        RouteActivity::class shouldNavigateTo CurrencyActivity::class
    }
}
