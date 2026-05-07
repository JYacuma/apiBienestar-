package com.example.bienestar.model

data class Solicitud(
    val id: Long? = null,
    val tipo: String? = null,
    val descripcion: String? = null, // Clave para que el 400 no vuelva
    val estado: String? = null,
    val createdAt: String? = null
)