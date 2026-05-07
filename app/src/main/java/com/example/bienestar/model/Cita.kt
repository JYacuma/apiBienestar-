package com.example.bienestar.model

import com.google.gson.annotations.SerializedName

data class Cita(
    val id: Long? = null,
    val fecha: String? = null,
    val motivo: String? = null,
    val estado: String? = "PENDIENTE",

    // Estos nombres deben coincidir con tu CitaDTO.Response de Spring Boot
    val nombreEstudiante: String? = null,
    val nombreProfesional: String? = null,
    val especialidad: String? = null,
    val horaInicio: String? = null,
    val dia: String? = null,

    @SerializedName("profesionalId") val profesionalId: Long? = null,
    @SerializedName("horarioId") val horarioId: Long? = null,
    @SerializedName("estudianteId") val estudianteId: Long? = null,
    @SerializedName("notaProfesional") val notaProfesional: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null
)