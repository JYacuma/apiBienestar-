package com.example.bienestar.model

import com.google.gson.annotations.SerializedName

data class Profesional(
    @SerializedName("id")
    val id: Long,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("especialidad")
    val especialidad: String,

    @SerializedName("correo")
    val correo: String? = null, // Lo ponemos opcional por si el JSON no lo trae

    // Opcional: Si en algún momento necesitas asociarlo con el ID de usuario
    @SerializedName("usuarioId")
    val usuarioId: Long? = null
)