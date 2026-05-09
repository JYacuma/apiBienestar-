package com.example.bienestar.repository

import com.example.bienestar.network.RetrofitClient

class EstudianteRepository {

    // 🎯 Ahora usa el ID en la URL dinámica
    suspend fun getCitas(id: Long) = RetrofitClient.apiService.getMisCitas(id)

    suspend fun getSolicitudes(id: Long) = RetrofitClient.apiService.getMisSolicitudes(id)

    // 🎯 Recibe el ID para agendar
    suspend fun agendarCita(id: Long, cita: Map<String, Any>) =
        RetrofitClient.apiService.agendarCita(id, cita)

    suspend fun pedirApoyo(id: Long, solicitud: Map<String, Any>) =
        RetrofitClient.apiService.crearSolicitud(id, solicitud)
}