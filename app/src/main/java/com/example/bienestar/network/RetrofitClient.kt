package com.example.bienestar.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit // ¡Esta línea es clave para medir el tiempo!

object RetrofitClient {

    private const val BASE_URL = "https://bienestar-1.onrender.com"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Aquí le damos paciencia a Android
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS) // Espera 60 segundos para conectar
        .readTimeout(60, TimeUnit.SECONDS)    // Espera 60 segundos para recibir datos
        .writeTimeout(60, TimeUnit.SECONDS)   // Espera 60 segundos para enviar datos
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}