package com.example.bienestar.ui.autenticacion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bienestar.R
import com.example.bienestar.model.Rol
import com.example.bienestar.model.Usuario
import com.example.bienestar.viewmodel.AutenticacionViewModel

class RegistroActivity : AppCompatActivity() {

    private val viewModel: AutenticacionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Referencias a los componentes del XML
        val etNombre = findViewById<EditText>(R.id.etNombreRegistro)
        val etCorreo = findViewById<EditText>(R.id.etCorreoRegistro)
        val etContra = findViewById<EditText>(R.id.etContrasenaRegistro)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val tvVolverLogin = findViewById<TextView>(R.id.tvVolverLogin)

        // Observamos el resultado del registro
        viewModel.registroResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "¡Registro exitoso! Ya puedes iniciar sesión", Toast.LENGTH_LONG).show()
                finish() // Volver al Login
            }.onFailure { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val contra = etContra.text.toString().trim()

            if (nombre.isNotEmpty() && correo.isNotEmpty() && contra.isNotEmpty()) {
                // Creamos el objeto usuario (Asegúrate que el modelo Usuario coincida con tu backend)
                val nuevoUsuario = Usuario(
                    id = 0,
                    nombre = nombre,
                    correo = correo,
                    contrasena = contra,
                    rol = "ESTUDIANTE",         // Como es un String, va entre comillas
                    programa = "No definido"    // ¡Este era el dato que faltaba!
                )
                viewModel.registrar(nuevoUsuario)
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        tvVolverLogin.setOnClickListener {
            finish()
        }
    }
}