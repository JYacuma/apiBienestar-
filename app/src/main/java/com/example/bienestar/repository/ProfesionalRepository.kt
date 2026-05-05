package com.example.bienestar.repository

import com.example.bienestar.model.Cita
import com.example.bienestar.network.RetrofitClient

class ProfesionalRepository {

    // Función para pedirle al servidor las citas asignadas a este profesional
    suspend fun obtenerCitasDelProfesional(profesionalId: Long): Result<List<Cita>> {
        return try {
            val respuesta = RetrofitClient.apiService.getCitasProfesional(profesionalId)

            if (respuesta.isSuccessful && respuesta.body() != null) {
                Result.success(respuesta.body()!!)
            } else {
                Result.failure(Exception("No se pudieron cargar las citas"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de red: Revisa tu internet"))
        }
    }
}