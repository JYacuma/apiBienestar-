package com.example.bienestar.ui.profesional

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bienestar.R
import com.example.bienestar.ui.autenticacion.LoginActivity
import com.example.bienestar.utils.SessionManager
import com.example.bienestar.viewmodel.ProfesionalViewModel

class ProfesionalMainActivity : AppCompatActivity() {

    private val viewModel: ProfesionalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesional_main)

        val sessionManager = SessionManager(this)
        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenidaProfesional)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesionProfesional)

        tvBienvenida.text = "Hola, ${sessionManager.getNombre()}\n(Profesional)"

        btnCerrarSesion.setOnClickListener {
            sessionManager.cerrarSesion()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}