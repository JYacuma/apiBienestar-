package com.example.bienestar.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.bienestar.R
import com.example.bienestar.ui.autenticacion.LoginActivity
import com.example.bienestar.ui.estudiante.EstudianteMainActivity
import com.example.bienestar.ui.profesional.ProfesionalMainActivity
import com.example.bienestar.utils.SessionManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Retrasamos la pantalla 2 segundos (2000 milisegundos)
        Handler(Looper.getMainLooper()).postDelayed({
            verificarSesion()
        }, 2000)
    }

    private fun verificarSesion() {
        val sessionManager = SessionManager(this)

        // Verificamos si hay una sesión activa revisando si el ID existe
        val idUsuario = sessionManager.obtenerIdUsuario()

        if (idUsuario != -1L) {
            // Usamos tu función getRol() en lugar de obtenerRol()
            val rol = sessionManager.getRol()

            if (rol == "PROFESIONAL") {
                startActivity(Intent(this, ProfesionalMainActivity::class.java))
            } else {
                startActivity(Intent(this, EstudianteMainActivity::class.java))
            }
        } else {
            // Si no hay sesión (id es -1L), va al Login
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Cerramos el Splash para que no puedan volver con el botón atrás
        finish()
    }
}