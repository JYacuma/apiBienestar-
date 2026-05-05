package com.example.bienestar.ui.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bienestar.R
import com.example.bienestar.ui.autenticacion.LoginActivity
import com.example.bienestar.utils.SessionManager
import com.example.bienestar.viewmodel.AdminViewModel

class AdminMainActivity : AppCompatActivity() {

    private val viewModel: AdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        val sessionManager = SessionManager(this)
        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenidaAdmin)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesionAdmin)

        tvBienvenida.text = "Hola, ${sessionManager.getNombre()}\n(Administrador)"

        btnCerrarSesion.setOnClickListener {
            sessionManager.cerrarSesion()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}