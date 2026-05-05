package com.example.bienestar.model

data class Reporte(
    val totalSolicitudes: Int,
    val solicitudesPendientes: Int,
    val citasCompletadas: Int,
    val citasCanceladas: Int
    // Estos campos dependerán exactamente de lo que tu Backend calcule
)