package com.example.bienestar.model

data class Horario(
    val id: Long,
    val activo: Boolean,
    val dia: String,
    val horaInicio: String, // Corresponde a hora_inicio
    val horaFin: String,    // Corresponde a hora_fin
    val profesionalId: Long
)