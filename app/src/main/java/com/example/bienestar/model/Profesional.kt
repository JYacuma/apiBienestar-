package com.example.bienestar.model

data class Profesional(
    val id: Long,
    val nombre: String,
    val correo: String,
    val rol: Rol,
    val especialidad: String
)