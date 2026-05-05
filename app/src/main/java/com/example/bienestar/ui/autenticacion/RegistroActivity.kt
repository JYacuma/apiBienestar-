package com.example.bienestar.ui.autenticacion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bienestar.R

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val tvVolverLogin = findViewById<TextView>(R.id.tvVolverLogin)

        btnRegistrar.setOnClickListener {
            Toast.makeText(this, "Función de registro en construcción...", Toast.LENGTH_SHORT).show()
            finish() // Cierra esta pantalla y vuelve al Login
        }

        tvVolverLogin.setOnClickListener {
            finish()
        }
    }
}