package com.example.bienestar.repository

import com.example.bienestar.network.RetrofitClient

class EstudianteRepository {
    private val api = RetrofitClient.apiService
    suspend fun getSolicitudes(id: Long) = api.getMisSolicitudes(id)
    suspend fun getCitas(id: Long) = api.getMisCitas(id)
}