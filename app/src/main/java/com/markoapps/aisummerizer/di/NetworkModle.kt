package com.markoapps.aisummerizer.di

import com.markoapps.aisummerizer.BuildConfig
import com.markoapps.aisummerizer.data.remote.api.OpenAiApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val OPEN_AI_API_KEY = BuildConfig.OPEN_AI_API_KEY
const val OPEN_AI_BASE_URL = "https://api.openai.com/"

val networkModule = module {
    single { provideOpenAiApi(apiKey = OPEN_AI_API_KEY, get()) }

    single {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
}

fun provideOpenAiApi(apiKey: String, moshi: Moshi): OpenAiApi {
    val client = OkHttpClient.Builder()
        .addInterceptor(OpenAiAuthInterceptor(apiKey))
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(OPEN_AI_BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi)) // 'get()' injects Moshi
        .build()

    return retrofit.create(OpenAiApi::class.java)
}

class OpenAiAuthInterceptor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}