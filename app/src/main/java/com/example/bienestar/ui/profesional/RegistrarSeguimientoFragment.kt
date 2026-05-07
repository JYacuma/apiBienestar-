package com.example.bienestar.ui.profesional

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bienestar.R
import com.example.bienestar.network.ApiClient
import com.example.bienestar.utils.SessionManager
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class RegistrarSeguimientoFragment : Fragment(R.layout.fragment_registrar_seguimiento) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(requireContext())
        val idProfesional = sessionManager.obtenerIdUsuario()

        val tvInfoPaciente = view.findViewById<TextView>(R.id.tvInfoPaciente)
        val etHistoria = view.findViewById<TextInputEditText>(R.id.etHistoriaClinica)
        val etDiagnostico = view.findViewById<TextInputEditText>(R.id.etDiagnostico)
        val etTratamiento = view.findViewById<TextInputEditText>(R.id.etTratamiento)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarSeguimiento)

        // --- CORRECCIÓN: NOMBRE E ID FORZADO A 1 ---
        val estudianteId = 1L // ID forzado como pediste
        val solicitudId = arguments?.getLong("CITA_ID") ?: -1L
        // Atrapamos el nombre que mandó el Adapter (o ponemos uno por defecto si falla)
        val nombrePaciente = arguments?.getString("NOMBRE_PACIENTE") ?: "Juan Perez"

        // Actualizamos la UI para que se vea elegante
        tvInfoPaciente.text = "Paciente: $nombrePaciente (ID: $estudianteId)"

        btnGuardar.setOnClickListener {
            val h = etHistoria.text.toString().trim()
            val d = etDiagnostico.text.toString().trim()
            val t = etTratamiento.text.toString().trim()

            if (h.isEmpty() || d.isEmpty() || t.isEmpty()) {
                Toast.makeText(requireContext(), "⚠️ Completa los 3 campos de información", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val notaUnificada = """
                HISTORIA CLÍNICA: $h
                DIAGNÓSTICO: $d
                PLAN DE ACCIÓN: $t
            """.trimIndent()

            val bodySeguimiento = hashMapOf<String, Any>(
                "solicitudId" to solicitudId,
                "profesionalId" to idProfesional,
                "nota" to notaUnificada
            )

            lifecycleScope.launch {
                try {
                    val response = ApiClient.apiService.registrarSeguimiento(bodySeguimiento)

                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "✅ Evolución guardada", Toast.LENGTH_LONG).show()
                        parentFragmentManager.popBackStack()
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "Error desconocido"
                        android.util.Log.e("SEGUIMIENTO_ERROR", "Error ${response.code()}: $errorMsg")
                        Toast.makeText(requireContext(), "❌ Error al guardar: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "⚠️ Error de red", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}