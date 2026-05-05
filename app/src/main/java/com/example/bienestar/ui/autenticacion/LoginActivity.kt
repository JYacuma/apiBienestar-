package com.example.bienestar.ui.autenticacion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

class LoginActivity : AppCompatActivity() {

    private val viewModel: AutenticacionViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etContra = findViewById<EditText>(R.id.etContrasena)
        val tvIrRegistro = findViewById<TextView>(R.id.tvIrRegistro)

        // Observador de la lógica REAL (Se activará cuando conectemos el Backend)
        viewModel.authResult.observe(this) { result ->
            result.onSuccess { usuario ->
                sessionManager.guardarSesion(usuario.id, usuario.nombre, usuario.rol.name)
                Toast.makeText(this, "Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()

                val intent = when (usuario.rol.name) {
                    "ESTUDIANTE" -> Intent(this, EstudianteMainActivity::class.java)
                    "PROFESIONAL" -> Intent(this, ProfesionalMainActivity::class.java)
                    else -> Intent(this, AdminMainActivity::class.java)
                }
                startActivity(intent)
                finish()
            }.onFailure { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
            }
        }

        btnLogin.setOnClickListener {
            // ==========================================
            // MODO DESARROLLO (BYPASS DEL LOGIN)
            // ==========================================
            // Entramos como PROFESIONAL para probar el clic en la agenda
            sessionManager.guardarSesion(2L, "Profesional de Prueba", "PROFESIONAL")

            Toast.makeText(this, "Modo Prueba: Entrando como Profesional...", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, ProfesionalMainActivity::class.java))
            finish()

            // ==========================================
            // LÓGICA REAL (Comentada temporalmente)
            // ==========================================
            /*
            val correo = etCorreo.text.toString().trim()
            val contra = etContra.text.toString().trim()
            if (correo.isNotEmpty() && contra.isNotEmpty()) {
                viewModel.login(correo, contra)
            } else {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            }
            */
        }

        tvIrRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
}