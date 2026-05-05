package com.example.bienestar.repository

import com.example.bienestar.model.Profesional
import com.example.bienestar.network.RetrofitClient

class AdminRepository {

    // Función para pedirle al servidor la lista de profesionales
    suspend fun obtenerProfesionales(): Result<List<Profesional>> {
        return try {
            val respuesta = RetrofitClient.apiService.getProfesionales()

            if (respuesta.isSuccessful && respuesta.body() != null) {
                Result.success(respuesta.body()!!)
            } else {
                Result.failure(Exception("No se pudieron cargar los profesionales"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión al servidor"))
        }
    }
}