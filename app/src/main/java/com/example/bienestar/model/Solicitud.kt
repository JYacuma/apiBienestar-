package com.example.bienestar.model

data class Solicitud(
    val id: Long,
    val estudianteId: Long,
    val tipo: TipoSolicitud,
    val estado: EstadoSolicitud,
    val descripcion: String,
    val fechaCreacion: String
)