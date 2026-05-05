package com.example.bienestar.model

data class Seguimiento(
    val id: Long,
    val solicitudId: Long,
    val profesionalId: Long,
    val observaciones: String,
    val fechaRegistro: String
)