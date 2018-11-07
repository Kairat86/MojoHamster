package com.hamster.mojo.manager

import com.hamster.mojo.model.Hamster
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

class ApiManager {

    companion object {
        private const val BASE_URL = "http://unrealmojo.com/porn/"
    }

    private val apiService: HamsterService by lazy {
        Retrofit.Builder()
            .client(OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS).build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(HamsterService::class.java)
    }

    fun getHamsters(clbk: Callback<List<Hamster>>) = apiService.get().enqueue(clbk)

    fun getHamsters() = apiService.get().execute()

    interface HamsterService {

        @GET("test3")
        fun get(): Call<List<Hamster>>
    }
}