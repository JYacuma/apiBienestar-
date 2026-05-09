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

        // 🎯 RECUPERAMOS EL ID REAL DE LA CITA SELECCIONADA
        val solicitudId = arguments?.getLong("CITA_ID") ?: -1L
        val nombrePaciente = arguments?.getString("NOMBRE_PACIENTE") ?: "Paciente"

        val tvInfo = view.findViewById<TextView>(R.id.tvInfoPaciente)
        tvInfo.text = "Paciente: $nombrePaciente"

        val etH = view.findViewById<TextInputEditText>(R.id.etHistoriaClinica)
        val etD = view.findViewById<TextInputEditText>(R.id.etDiagnostico)
        val etT = view.findViewById<TextInputEditText>(R.id.etTratamiento)
        val btn = view.findViewById<Button>(R.id.btnGuardarSeguimiento)

        btn.setOnClickListener {
            val nota = "HISTORIA: ${etH.text}\nDIAGNOSTICO: ${etD.text}\nTRATAMIENTO: ${etT.text}"

            if (solicitudId == -1L) {
                Toast.makeText(requireContext(), "❌ Error: ID de cita no válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val body = hashMapOf<String, Any>(
                "solicitudId" to solicitudId,
                "profesionalId" to idProfesional,
                "nota" to nota
            )

            lifecycleScope.launch {
                try {
                    val resp = ApiClient.apiService.registrarSeguimiento(body)
                    if (resp.isSuccessful) {
                        Toast.makeText(requireContext(), "✅ Evolución guardada", Toast.LENGTH_LONG).show()
                        parentFragmentManager.popBackStack()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "⚠️ Fallo de red", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}