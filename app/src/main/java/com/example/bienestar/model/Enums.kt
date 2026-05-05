package com.example.bienestar.model

enum class Rol {
    ESTUDIANTE,
    PROFESIONAL,
    ADMINISTRADOR
}

enum class EstadoCita {
    PENDIENTE,
    ASISTIO,
    NO_ASISTIO,
    CANCELADA
}

enum class EstadoSolicitud {
    PENDIENTE,
    EN_PROCESO,
    CERRADA
}

enum class TipoSolicitud {
    ACADEMICA,
    EMOCIONAL,
    SOCIAL
}