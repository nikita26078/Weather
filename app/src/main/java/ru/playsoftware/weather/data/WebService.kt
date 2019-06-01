package ru.playsoftware.weather.data

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WebService {
    @GET("data/2.5/forecast/daily?q=Saint%20Petersburg&units=metric&cnt=16&lang=ru&APPID=a187f04c8f8dca5c1da48a3785da9312")
    fun getWeather(): Single<WeatherData>

    companion object Factory {
        fun create(): WebService {

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("http://api.openweathermap.org")
                    .build()

            return retrofit.create(WebService::class.java)
        }
    }
}