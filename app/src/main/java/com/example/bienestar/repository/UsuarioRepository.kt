package com.example.bienestar.repository

import com.example.bienestar.network.RetrofitClient
import com.example.bienestar.model.Usuario

class UsuarioRepository {
    private val api = RetrofitClient.apiService
    suspend fun login(correo: String, contra: String) = api.loginUsuario(mapOf("correo" to correo, "contrasena" to contra))
}