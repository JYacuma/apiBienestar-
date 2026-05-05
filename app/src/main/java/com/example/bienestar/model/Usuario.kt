package com.example.bienestar.model

data class Usuario(
    val id: Long,
    val nombre: String,
    val correo: String,
    val contrasena: String?,
    val rol: Rol,
    val programa: String?
)