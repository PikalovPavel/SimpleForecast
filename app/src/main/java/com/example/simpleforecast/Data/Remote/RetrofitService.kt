package com.example.simpleforecast.Data.Remote

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


const val BASE_URL: String = "https://dataservice.accuweather.com/"

object RetrofitService {

    fun create(): ForecastApi = createApi(HttpUrl.parse(BASE_URL)!!)




    private fun createApi(httpUrl: HttpUrl): ForecastApi {

        val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttp = OkHttpClient.Builder()
                .addInterceptor(logInterceptor)

            okHttp.interceptors().add(Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url()
                .newBuilder()
                .addQueryParameter("apikey", "eCnII7fEZHZlVY3eVGYWYIURh7YMDrxi")
                .addQueryParameter("language", "ru-ru")
                .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        })


        return Retrofit.Builder()
            .baseUrl(httpUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttp.build())
            .build()
            .create(ForecastApi::class.java)
    }
}
