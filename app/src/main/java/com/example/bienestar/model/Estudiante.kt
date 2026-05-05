package com.example.bienestar.model

data class Estudiante(
    val id: Long,
    val nombre: String,
    val correo: String,
    val rol: Rol,
    val codigo: String,
    val programa: String
)