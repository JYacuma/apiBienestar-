package com.example.bienestar.ui.profesional

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bienestar.R
import com.example.bienestar.network.RetrofitClient // <-- Ajusta esto al nombre de tu cliente Retrofit
import com.example.bienestar.ui.autenticacion.LoginActivity
import com.example.bienestar.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InicioProfesionalFragment : Fragment(R.layout.fragment_inicio_profesional) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(requireContext())
        val tvBienvenida = view.findViewById<TextView>(R.id.tvBienvenidaProf)
        val tvDetallesCita = view.findViewById<TextView>(R.id.tvDetallesCitaProf)
        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesionProf)

        // Nombre real desde la sesión
        val nombre = sessionManager.getNombre() ?: "Profesional"
        tvBienvenida.text = "¡Hola, $nombre!"

        // Ponemos un texto de "Cargando..." mientras el servidor responde
        tvDetallesCita.text = "Cargando próxima atención..."

        // 🎯 AQUI ESTÁ LA MAGIA: Llamamos a la API de verdad
        cargarProximaCita(sessionManager, tvDetallesCita)

        btnCerrarSesion.setOnClickListener {
            sessionManager.cerrarSesion()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun cargarProximaCita(sessionManager: SessionManager, tvDetallesCita: TextView) {

        // ⚠️ REVISA ESTA LÍNEA: Cambia 'getUsuarioId()' por el método real que tengas en tu SessionManager.
        // Si se llama getId, fíjate que esté bien escrito en SessionManager.kt
        val usuarioId = sessionManager.obtenerIdUsuario() ?: 3L

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getCitasProfesional(usuarioId)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val citas = response.body()!!

                        val proximaCita = citas.firstOrNull { it.estado == "PENDIENTE" }

                        if (proximaCita != null) {
                            // 🎯 FIX: Usamos nombreEstudiante y estudianteId, que SÍ existen en tu Cita.kt
                            tvDetallesCita.text = "📅 Fecha: ${proximaCita.fecha} | ${proximaCita.horaInicio ?: "Sin hora"}\n" +
                                    "👤 Estudiante: ${proximaCita.nombreEstudiante ?: "ID: ${proximaCita.estudianteId}"}\n" +
                                    "📋 Motivo: ${proximaCita.motivo ?: "Sin motivo especificado"}"
                        } else {
                            tvDetallesCita.text = "Tu agenda está libre en este momento. ¡Buen trabajo!"
                        }
                    } else {
                        tvDetallesCita.text = "Error al cargar tu agenda."
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    tvDetallesCita.text = "Sin conexión al servidor."
                    e.printStackTrace()
                }
            }
        }
    }
}