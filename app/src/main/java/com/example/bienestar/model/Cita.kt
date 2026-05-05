package com.example.bienestar.model

data class Cita(
    val id: Long,
    val estudianteId: Long,
    val profesionalId: Long,
    val fecha: String, // Formato YYYY-MM-DD
    val hora: String,  // Formato HH:mm:ss
    val estado: EstadoCita,
    val notas: String?
)