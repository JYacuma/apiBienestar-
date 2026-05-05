package com.example.bienestar.network

import com.example.bienestar.model.Cita
import com.example.bienestar.model.Profesional
import com.example.bienestar.model.Solicitud
import com.example.bienestar.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {


    @POST("api/usuarios/login")
    suspend fun loginUsuario(@Body credenciales: Map<String, String>): Response<Usuario>

    @POST("api/usuarios/registro")
    suspend fun registrarUsuario(@Body nuevoUsuario: Usuario): Response<Usuario>

    @GET("api/estudiante/{id}/citas")
    suspend fun getMisCitas(@Path("id") id: Long): Response<List<Cita>>

    @GET("api/estudiante/{id}/solicitudes")
    suspend fun getMisSolicitudes(@Path("id") id: Long): Response<List<Solicitud>>

    @GET("api/profesional/{id}/citas")
    suspend fun getCitasProfesional(@Path("id") id: Long): Response<List<Cita>>

    @GET("api/admin/profesionales")
    suspend fun getProfesionales(): Response<List<Profesional>>
}