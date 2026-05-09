package com.example.bienestar.network

import com.example.bienestar.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ─── 1. AUTENTICACIÓN (UsuarioController) ──────────────────────
    @POST("api/auth/login")
    suspend fun loginUsuario(@Body credenciales: Map<String, String>): Response<Usuario>

    @POST("api/auth/registro/estudiante")
    suspend fun registrarEstudiante(@Body datos: Map<String, String>): Response<Usuario>

    @POST("api/auth/registro/profesional")
    suspend fun registrarProfesional(@Body datos: Map<String, String>): Response<Usuario>


    // ─── 2. MÓDULO ESTUDIANTE (Citas y Apoyos) ─────────────────────

    @POST("api/citas/estudiante/{id}")
    suspend fun agendarCita(
        @Path("id") id: Long,
        @Body cita: Map<String, @JvmSuppressWildcards Any>
    ): Response<Cita>

    @GET("api/citas/estudiante/{id}")
    suspend fun getMisCitas(@Path("id") id: Long): Response<List<Cita>>

    @POST("api/solicitudes/estudiante/{id}")
    suspend fun crearSolicitud(
        @Path("id") id: Long,
        @Body nuevaSolicitud: Map<String, @JvmSuppressWildcards Any>
    ): Response<Solicitud>

    @GET("api/solicitudes/estudiante/{id}")
    suspend fun getMisSolicitudes(@Path("id") id: Long): Response<List<Solicitud>>


    // ─── 3. MÓDULO PROFESIONAL (Gestión Propia) ────────────────────

    @GET("api/profesional/{id}/citas")
    suspend fun getCitasProfesional(@Path("id") id: Long): Response<List<Cita>>

    @GET("api/profesional/{id}/horarios")
    suspend fun getHorariosProfesional(@Path("id") id: Long): Response<List<Horario>>

    @POST("api/profesional/{id}/horarios")
    suspend fun crearHorarioProfesional(
        @Path("id") id: Long,
        @Body horario: Map<String, @JvmSuppressWildcards Any>
    ): Response<Horario>

    @PUT("api/profesional/citas/{citaId}/estado")
    suspend fun actualizarEstadoCita(
        @Path("citaId") citaId: Long,
        @Body estadoBody: Map<String, String>
    ): Response<Cita>

    // 🎯 EL GRAN HPTA QUE FALTABA: Registrar Evolución/Seguimiento
    @POST("api/seguimientos")
    suspend fun registrarSeguimiento(
        @Body nuevoSeguimiento: Map<String, @JvmSuppressWildcards Any>
    ): Response<Any>


    // ─── 4. MÓDULO ADMINISTRADOR (Dashboard Global) ────────────────

    @GET("api/admin/usuarios")
    suspend fun getTodosLosUsuarios(): Response<List<Usuario>>

    @GET("api/admin/profesionales")
    suspend fun getTodosLosProfesionales(): Response<List<Profesional>>

    @GET("api/admin/citas")
    suspend fun getTodasLasCitas(): Response<List<Cita>>

    @GET("api/admin/solicitudes")
    suspend fun getTodasLasSolicitudes(): Response<List<Solicitud>>

    @POST("api/admin/horarios")
    suspend fun crearHorarioAdmin(@Body nuevoHorario: Map<String, @JvmSuppressWildcards Any>): Response<Horario>

    @DELETE("api/admin/horarios/{id}")
    suspend fun eliminarHorario(@Path("id") id: Long): Response<Void>

    @GET("api/admin/horarios/profesional/{profesionalId}")
    suspend fun getHorariosPorProfesional(@Path("profesionalId") profesionalId: Long): Response<List<Horario>>


    // ─── 5. EXTRAS ────────────────────────────────────────────────

    @PUT("api/solicitudes/{id}/estado")
    suspend fun actualizarEstadoSolicitud(
        @Path("id") id: Long,
        @Body estadoBody: Map<String, String>
    ): Response<Solicitud>
}