package com.example.bienestar.repository

import com.example.bienestar.model.Cita
import com.example.bienestar.model.Solicitud
import com.example.bienestar.network.RetrofitClient

class EstudianteRepository {
    private val api = RetrofitClient.apiService

    // Funciones para OBTENER (GET)
    suspend fun getSolicitudes(id: Long) = api.getMisSolicitudes(id)

    // CORREGIDO: Se quita el 'id' dentro del paréntesis de la llamada a la API
    suspend fun getCitas(id: Long) = api.getMisCitas()

    // Funciones para ENVIAR (POST)
    // CORREGIDO: Ahora recibe el Map (citaData) y no envía el estudianteId por separado
    suspend fun agendarCita(citaData: Map<String, Any>) = api.agendarCita(citaData)

    // En EstudianteRepository.kt
    suspend fun pedirApoyo(estudianteId: Long, solicitudData: Map<String, Any>) =
        api.pedirApoyo(estudianteId, solicitudData)
}