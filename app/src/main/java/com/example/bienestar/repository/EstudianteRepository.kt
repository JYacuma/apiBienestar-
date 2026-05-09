package com.example.bienestar.repository

import com.example.bienestar.network.RetrofitClient // O ApiClient, el que uses para llamar a la API
import com.example.bienestar.model.Cita
import com.example.bienestar.model.Solicitud
import retrofit2.Response

class EstudianteRepository {

    // 📅 CITAS
    suspend fun getCitas(id: Long): Response<List<Cita>> =
        RetrofitClient.apiService.getMisCitas(id)

    suspend fun agendarCita(id: Long, cita: Map<String, Any>): Response<Cita> =
        RetrofitClient.apiService.agendarCita(id, cita)

    // 🆘 SOLICITUDES (APOYO)
    suspend fun getSolicitudes(id: Long): Response<List<Solicitud>> =
        RetrofitClient.apiService.getMisSolicitudes(id)

    suspend fun pedirApoyo(id: Long, solicitud: Map<String, Any>): Response<Solicitud> =
        RetrofitClient.apiService.crearSolicitud(id, solicitud)
}