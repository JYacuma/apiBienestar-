package com.example.bienestar.network

import com.example.bienestar.model.*
import retrofit2.Response
import retrofit2.http.* // <-- Este asterisco es la clave, importa GET, POST, Body, Path, todo de una vez.

interface ApiService {
    // Auth & Usuarios
    @POST("api/usuarios/login")
    suspend fun loginUsuario(@Body credenciales: Map<String, String>): Response<Usuario>

    @POST("api/usuarios/registro")
    suspend fun registrarUsuario(@Body nuevoUsuario: Usuario): Response<Usuario>

    // Estudiante
    @GET("api/estudiante/{id}/solicitudes")
    suspend fun getMisSolicitudes(@Path("id") id: Long): Response<List<Solicitud>>

    @GET("api/estudiante/{id}/citas")
    suspend fun getMisCitas(@Path("id") id: Long): Response<List<Cita>>

    // Profesional
    @GET("api/profesional/{id}/citas")
    suspend fun getCitasProfesional(@Path("id") id: Long): Response<List<Cita>>

    // Admin
    @GET("api/admin/profesionales")
    suspend fun getProfesionales(): Response<List<Profesional>>
}