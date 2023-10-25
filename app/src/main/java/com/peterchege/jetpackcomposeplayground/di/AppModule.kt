package com.peterchege.jetpackcomposeplayground.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.peterchege.jetpackcomposeplayground.api.ApiService
import com.peterchege.jetpackcomposeplayground.repository.PostRepository
import com.peterchege.jetpackcomposeplayground.repository.PostRepositoryImpl
import com.peterchege.jetpackcomposeplayground.util.Constants
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

interface AppModule {
    val apiService: ApiService
    val postRepository :PostRepository
}


class AppModuleImpl(
    private val appContext: Context
): AppModule {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    override val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        ChuckerInterceptor.Builder(context = appContext)
                            .collector(ChuckerCollector(context = appContext))
                            .maxContentLength(length = 250000L)
                            .redactHeaders(headerNames = emptySet())
                            .alwaysReadResponseBody(enable = false)
                            .build()
                    )
                    .addInterceptor(
                        HttpLoggingInterceptor().also {
                            it.level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .build()
            )
            .build()
            .create()
    }
    override val postRepository: PostRepository by lazy {
        PostRepositoryImpl(apiService)
    }
}