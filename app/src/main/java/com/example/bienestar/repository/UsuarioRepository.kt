package com.example.bienestar.repository

import com.example.bienestar.network.RetrofitClient

class UsuarioRepository {

    suspend fun registrarEstudiante(datos: Map<String, String>) =
        RetrofitClient.apiService.registrarEstudiante(datos)

    suspend fun registrarProfesional(datos: Map<String, String>) =
        RetrofitClient.apiService.registrarProfesional(datos)

}