package com.example.bienestar.repository

import com.example.bienestar.model.*
import com.example.bienestar.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdminRepository {

    // 1. Obtener lista de profesionales (Sincronizado con getTodosLosProfesionales)
    suspend fun obtenerProfesionales(): Result<List<Profesional>> {
        return withContext(Dispatchers.IO) {
            try {
                val respuesta = ApiClient.apiService.getTodosLosProfesionales()
                if (respuesta.isSuccessful) {
                    Result.success(respuesta.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Error al obtener profesionales: ${respuesta.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 2. Obtener todos los usuarios registrados
    suspend fun obtenerUsuarios(): Result<List<Usuario>> {
        return withContext(Dispatchers.IO) {
            try {
                val respuesta = ApiClient.apiService.getTodosLosUsuarios()
                if (respuesta.isSuccessful) {
                    Result.success(respuesta.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Error al obtener usuarios: ${respuesta.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 3. Obtener todas las citas del sistema
    suspend fun obtenerTodasLasCitas(): Result<List<Cita>> {
        return withContext(Dispatchers.IO) {
            try {
                val respuesta = ApiClient.apiService.getTodasLasCitas()
                if (respuesta.isSuccessful) {
                    Result.success(respuesta.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Error al obtener citas: ${respuesta.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 4. Obtener todas las solicitudes de apoyo (La tabla que acabamos de probar)
    suspend fun obtenerTodasLasSolicitudes(): Result<List<Solicitud>> {
        return withContext(Dispatchers.IO) {
            try {
                val respuesta = ApiClient.apiService.getTodasLasSolicitudes()
                if (respuesta.isSuccessful) {
                    Result.success(respuesta.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Error al obtener solicitudes: ${respuesta.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 5. Eliminar un horario
    suspend fun borrarHorario(id: Long): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val respuesta = ApiClient.apiService.eliminarHorario(id)
                if (respuesta.isSuccessful) {
                    Result.success(true)
                } else {
                    Result.failure(Exception("No se pudo eliminar el horario"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}