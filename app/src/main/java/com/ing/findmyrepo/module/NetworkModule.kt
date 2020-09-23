package com.ing.findmyrepo.module

import com.ing.findmyrepo.data.network.RepoApi
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule : KoinComponent {

    val networkModule = module {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val timeoutInterval = 10
        val httpClientWithHeader = OkHttpClient.Builder()
        httpClientWithHeader.addInterceptor(logging)
        httpClientWithHeader.connectTimeout(timeoutInterval.toLong(), TimeUnit.SECONDS)
        httpClientWithHeader.readTimeout(timeoutInterval.toLong(), TimeUnit.SECONDS)

        val client = httpClientWithHeader.build()

        factory { OkHttpClient.Builder().protocols(arrayListOf(Protocol.HTTP_1_1)).build() }

        single {
            Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        (factory { get<Retrofit>().create(RepoApi::class.java) })
    }
}