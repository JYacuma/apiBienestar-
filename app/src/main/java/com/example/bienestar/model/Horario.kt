package com.example.bienestar.model

data class Horario(
    val id: Long,
    val profesionalId: Long,
    val dia: String,
    val horaInicio: String,
    val horaFin: String
)