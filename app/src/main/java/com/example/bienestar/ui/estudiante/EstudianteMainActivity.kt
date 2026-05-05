package com.example.bienestar.ui.estudiante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bienestar.R
import com.example.bienestar.ui.autenticacion.LoginActivity
import com.example.bienestar.utils.SessionManager
import com.example.bienestar.viewmodel.EstudianteViewModel

class EstudianteMainActivity : AppCompatActivity() {

    private val viewModel: EstudianteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante_main)

        val sessionManager = SessionManager(this)
        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenidaEstudiante)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesionEstudiante)

        tvBienvenida.text = "Hola, ${sessionManager.getNombre()}\n(Estudiante)"

        btnCerrarSesion.setOnClickListener {
            sessionManager.cerrarSesion()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}