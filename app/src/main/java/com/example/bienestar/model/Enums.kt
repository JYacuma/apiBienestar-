package com.example.bienestar.model

enum class Rol {
    ESTUDIANTE,
    PROFESIONAL,
    ADMINISTRADOR
}



enum class EstadoCita {
    PENDIENTE,
    CONFIRMADA,
    CANCELADA,
    COMPLETADA
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