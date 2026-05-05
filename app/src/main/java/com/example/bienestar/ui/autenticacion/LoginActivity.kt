package com.example.bienestar.ui.autenticacion

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bienestar.R
import com.example.bienestar.model.Rol
import com.example.bienestar.ui.admin.AdminMainActivity
import com.example.bienestar.ui.estudiante.EstudianteMainActivity
import com.example.bienestar.ui.profesional.ProfesionalMainActivity
import com.example.bienestar.viewmodel.AutenticacionViewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: AutenticacionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etContra = findViewById<EditText>(R.id.etContrasena)
        val tvIrRegistro = findViewById<TextView>(R.id.tvIrRegistro)

        // Escuchar respuesta del servidor
        viewModel.authResult.observe(this) { result ->
            result.onSuccess { usuario ->
                Toast.makeText(this, "¡Bienvenido ${usuario.nombre}!", Toast.LENGTH_SHORT).show()

                // Redirección por ROL
                val intent = when (usuario.rol) {
                    Rol.ADMINISTRADOR -> Intent(this, AdminMainActivity::class.java)
                    Rol.ESTUDIANTE -> Intent(this, EstudianteMainActivity::class.java)
                    Rol.PROFESIONAL -> Intent(this, ProfesionalMainActivity::class.java)
                }
                startActivity(intent)
                finish()
            }.onFailure { error ->
                // Esto te avisará si es 404, 401 o falta internet
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }

        btnLogin.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contra = etContra.text.toString().trim()
            if (correo.isNotEmpty() && contra.isNotEmpty()) {
                viewModel.login(correo, contra)
            } else {
                Toast.makeText(this, "Escribe correo y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        tvIrRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
}