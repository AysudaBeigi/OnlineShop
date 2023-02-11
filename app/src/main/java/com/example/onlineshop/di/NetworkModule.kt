package com.example.onlineshop.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 15L
private const val WRITE_TIMEOUT = 15L
private const val READ_TIMEOUT = 15L
val retrofitModule = module {
    single { retrofitHttpClient() }
    single { retrofitBuilder() }
    single { getLogger() }

}

fun getLogger(): Interceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY
    return logger
}


@OptIn(ExperimentalSerializationApi::class)
private fun Scope.retrofitBuilder(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(
            Json(builderAction = {
                ignoreUnknownKeys = true
            }
            ).asConverterFactory(MediaType.get("application/json"))
        )
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(get())
        .build()
}

private fun Scope.retrofitHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
        addInterceptor(get())
    }.build()
}
