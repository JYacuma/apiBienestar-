package com.example.bienestar.repository

import com.example.bienestar.model.Usuario
import com.example.bienestar.network.RetrofitClient

class UsuarioRepository {
    suspend fun registrar(usuario: Usuario) = RetrofitClient.apiService.registrarUsuario(usuario)
}