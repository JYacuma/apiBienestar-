package com.example.bienestar.ui.estudiante

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bienestar.R
import com.example.bienestar.ui.autenticacion.LoginActivity
import com.example.bienestar.utils.SessionManager
import com.example.bienestar.viewmodel.EstudianteViewModel

// ... (mismos imports)

class InicioEstudianteFragment : Fragment(R.layout.fragment_inicio_estudiante) {

    private lateinit var viewModel: EstudianteViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(requireContext())
        val tvBienvenida = view.findViewById<TextView>(R.id.tvBienvenidaInicio)
        val tvDetallesCita = view.findViewById<TextView>(R.id.tvDetallesCitaEstudiante)
        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesion)

        tvBienvenida.text = "¡Hola, ${sessionManager.getNombre() ?: "Usuario"}!"

        viewModel = ViewModelProvider(requireActivity()).get(EstudianteViewModel::class.java)

        // OBSERVAR: Lógica para el recordatorio de la próxima cita
        viewModel.citas.observe(viewLifecycleOwner) { listaDeCitas ->
            if (listaDeCitas != null) {
                // Buscamos la primera cita que no esté cancelada
                val proximaCita = listaDeCitas.firstOrNull { cita -> cita.estado != "CANCELADA" }

                if (proximaCita != null) {
                    // Ahora todas estas variables ya existen en el modelo Cita.kt
                    tvDetallesCita.text = "📅 Fecha: ${proximaCita.fecha} | ${proximaCita.horaInicio ?: "--:--"}\n" +
                            "👨‍⚕️ Profesional: ${proximaCita.nombreProfesional ?: "Asignado"}\n" +
                            "🏥 Especialidad: ${proximaCita.especialidad ?: "Bienestar"}\n" +
                            "📌 Motivo: ${proximaCita.motivo}\n" +
                            "✅ Estado: ${proximaCita.estado}"
                } else {
                    tvDetallesCita.text = "No tienes citas programadas por el momento. Agenda una en el menú de citas."
                }
            }
        }

        viewModel.cargarDatos(1L)

        btnCerrarSesion.setOnClickListener {
            sessionManager.cerrarSesion()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}