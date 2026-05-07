package com.example.bienestar.network

import com.example.bienestar.model.Cita
import com.example.bienestar.model.Horario
import com.example.bienestar.model.Profesional
import com.example.bienestar.model.Seguimiento
import com.example.bienestar.model.Solicitud
import com.example.bienestar.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // ─── AUTENTICACIÓN (UsuarioController) ───────────────────────────

    @POST("api/auth/login")
    suspend fun loginUsuario(@Body credenciales: Map<String, String>): Response<Usuario>

    @POST("api/auth/registro")
    suspend fun registrarUsuario(@Body nuevoUsuario: Usuario): Response<Usuario>


    // ─── CITAS (CitaController) ──────────────────────────────────────

    // CORREGIDO: Ruta exacta del controller, sin Path ID, y recibe el Map
    @POST("api/citas")
    suspend fun agendarCita(
        @Body cita: Map<String, @JvmSuppressWildcards Any>
    ): Response<Cita>

    // CORREGIDO: Ruta para traer las citas del estudiante (ID 1 fijo en el backend)
    @GET("api/citas/estudiante")
    suspend fun getMisCitas(): Response<List<Cita>>


    // ─── ESTUDIANTE (EstudianteController) ───────────────────────────

    @POST("api/estudiante/{id}/solicitudes")
    suspend fun pedirApoyo(
        @Path("id") id: Long,
        @Body nuevaSolicitud: Map<String, @JvmSuppressWildcards Any> // Cambiado a Map
    ): Response<Solicitud>

    @GET("api/estudiante/{id}/solicitudes")
    suspend fun getMisSolicitudes(@Path("id") id: Long): Response<List<Solicitud>>


    // ─── PROFESIONAL (ProfesionalController / SeguimientoController) ───

    @GET("api/profesional/{id}/citas")
    suspend fun getCitasProfesional(@Path("id") id: Long): Response<List<Cita>>

    @PUT("api/profesional/citas/{citaId}/estado")
    suspend fun actualizarEstadoCita(
        @Path("citaId") citaId: Long,
        @Body estadoBody: Map<String, String>
    ): Response<Cita>

    @GET("api/profesional/{id}/horarios")
    suspend fun getHorariosProfesional(@Path("id") id: Long): Response<List<Horario>>

    // CORRECCIÓN: Ruta exacta de Spring Boot y uso de Map para evitar el Error 400
    @POST("api/seguimientos")
    suspend fun registrarSeguimiento(
        @Body nuevoSeguimiento: Map<String, @JvmSuppressWildcards Any>
    ): Response<Any> // Response<Any> porque el servidor devuelve el DTO.Response y no necesitamos mapear todo de vuelta en la app


    // ─── ADMINISTRADOR (AdminController) ─────────────────────────────

    @GET("api/admin/profesionales")
    suspend fun getProfesionales(): Response<List<Profesional>>

    @GET("api/admin/citas")
    suspend fun getTodasLasCitas(): Response<List<Cita>>

    @GET("api/admin/solicitudes")
    suspend fun getTodasLasSolicitudes(): Response<List<Solicitud>>
}