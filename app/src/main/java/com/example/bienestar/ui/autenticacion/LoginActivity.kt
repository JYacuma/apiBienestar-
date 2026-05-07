package com.example.bienestar.ui.autenticacion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bienestar.R
import com.example.bienestar.ui.admin.AdminMainActivity
import com.example.bienestar.ui.estudiante.EstudianteMainActivity
import com.example.bienestar.ui.profesional.ProfesionalMainActivity
import com.example.bienestar.utils.SessionManager
import com.example.bienestar.viewmodel.AutenticacionViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private val viewModel: AutenticacionViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etCorreo = findViewById<TextInputEditText>(R.id.etCorreo)
        val etContra = findViewById<TextInputEditText>(R.id.etContrasena)
        val tvIrRegistro = findViewById<TextView>(R.id.tvIrRegistro)

        // Obtenemos el "contenedor" de diseño para poder pintarlo de rojo si hay error
        val tilCorreo = etCorreo.parent.parent as TextInputLayout
        val tilContra = etContra.parent.parent as TextInputLayout

        viewModel.authResult.observe(this) { result ->
            result.onSuccess { usuario ->
                sessionManager.guardarSesion(usuario.id, usuario.nombre, usuario.rol)

                val intent = when (usuario.rol) {
                    "ESTUDIANTE" -> Intent(this, EstudianteMainActivity::class.java)
                    "PROFESIONAL" -> Intent(this, ProfesionalMainActivity::class.java)
                    "ADMIN", "ADMINISTRADOR" -> Intent(this, AdminMainActivity::class.java)
                    else -> Intent(this, EstudianteMainActivity::class.java)
                }

                startActivity(intent)
                finish()
            }.onFailure {
                // En lugar de un Toast, marcamos la caja de contraseña con el error
                tilContra.error = "Credenciales incorrectas"
            }
        }

        btnLogin.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contra = etContra.text.toString().trim()

            // Limpiar errores anteriores
            tilCorreo.error = null
            tilContra.error = null
            var hayError = false

            if (correo.isEmpty()) {
                tilCorreo.error = "El correo es obligatorio"
                hayError = true
            }
            if (contra.isEmpty()) {
                tilContra.error = "La contraseña es obligatoria"
                hayError = true
            }

            // Si ambos campos están llenos, llamamos al backend
            if (!hayError) {
                viewModel.login(correo, contra)
            }
        }

        tvIrRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
}