package com.example.bienestar.ui.autenticacion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bienestar.R

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Solo carga el contenedor, la navegación hace el resto
        setContentView(R.layout.activity_registro)
    }
}