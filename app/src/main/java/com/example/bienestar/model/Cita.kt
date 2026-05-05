package com.example.bienestar.model

data class Cita(
    val id: Long,
    val estudianteId: Long,
    val profesionalId: Long,
    val fecha: String,
    val hora: String,
    val estado: EstadoCita,
    val notas: String?
)