package com.example.bienestar.model

data class Solicitud(
    val id: Long? = null,
    val tipo: String? = null,
    val descripcion: String? = null,
    val estado: String? = null,
    val nombreProfesional: String? = null, // 🎯 Agregado para coincidir con el Backend
    val createdAt: String? = null
)