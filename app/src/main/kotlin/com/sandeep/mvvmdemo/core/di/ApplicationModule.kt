
package com.sandeep.mvvmdemo.core.di

import android.content.Context
import com.google.gson.Gson
import com.sandeep.mvvmdemo.MVVMDemoApp
import com.sandeep.mvvmdemo.BuildConfig
import com.sandeep.mvvmdemo.feature.data.local.CacheHelper
import com.sandeep.mvvmdemo.feature.data.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MVVMDemoApp) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://api.currencylayer.com/")
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides @Singleton fun provideCurrencyRepository(dataSource: CurrencyRepository.Network): CurrencyRepository = dataSource

    @Provides @Singleton fun provideCacheHelper(context: Context): CacheHelper = CacheHelper(context)

    @Provides @Singleton fun provideGson(): Gson = Gson()

}
