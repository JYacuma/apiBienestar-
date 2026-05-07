package com.example.bienestar.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("BienestarPrefs", Context.MODE_PRIVATE)

    fun guardarSesion(id: Long, nombre: String, rol: String) {
        val editor = prefs.edit()
        editor.putLong("usuarioId", id)
        editor.putString("nombre", nombre)
        editor.putString("rol", rol)
        editor.apply()
    }

    // Cambiado a obtenerIdUsuario para que los fragmentos dejen de estar en rojo
    fun obtenerIdUsuario(): Long = prefs.getLong("usuarioId", -1L)

    fun getRol(): String? = prefs.getString("rol", null)
    fun getNombre(): String? = prefs.getString("nombre", null)

    fun cerrarSesion() {
        prefs.edit().clear().apply()
    }
}