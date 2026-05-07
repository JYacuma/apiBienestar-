package com.example.bienestar.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // IMPORTANTE: Si usas emulador, usa 10.0.2.2. Si usas móvil físico, usa tu IP local.
    private const val BASE_URL = "https://bienestar-1.onrender.com"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}