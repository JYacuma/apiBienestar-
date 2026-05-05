
package com.example.bienestar.model

data class LoginRequest(
    val correo: String,
    val contrasena: String
)

data class LoginResponse(
    val id: Long,
    val nombre: String,
    val correo: String,
    val rol: String
)