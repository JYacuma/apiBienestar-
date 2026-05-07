package com.example.bienestar.model

data class Seguimiento(
    val id: Long? = null,             // Mapea a id (int8)
    val fechaRegistro: String? = null,// Mapea a fecha_registro (timestamp)
    val nota: String,                 // Mapea a nota (text)
    val profesionalId: Long,          // Mapea a profesional_id (int8)
    val solicitudId: Long             // Mapea a solicitud_id (int8)
)