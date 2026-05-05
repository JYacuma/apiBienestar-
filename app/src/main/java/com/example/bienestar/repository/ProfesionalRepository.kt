package com.example.bienestar.repository

import com.example.bienestar.network.RetrofitClient

class ProfesionalRepository {
    private val api = RetrofitClient.apiService
    suspend fun getCitas(id: Long) = api.getCitasProfesional(id)
}