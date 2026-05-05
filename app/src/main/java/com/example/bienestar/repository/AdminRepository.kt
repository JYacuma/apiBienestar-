package com.example.bienestar.repository

import com.example.bienestar.network.RetrofitClient

class AdminRepository {
    private val api = RetrofitClient.apiService
    suspend fun listarProfesionales() = api.getProfesionales()
}