package com.example.bienestar.ui.profesional

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bienestar.R
import com.example.bienestar.model.Cita
import com.example.bienestar.ui.adapters.CitaAdapter

class AgendaProfesionalFragment : Fragment(R.layout.fragment_agenda_profesional) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvAgenda = view.findViewById<RecyclerView>(R.id.rvAgendaProf)
        rvAgenda.layoutManager = LinearLayoutManager(requireContext())

        // Datos de prueba para el Profesional 1
        val citasFalsas = listOf(
            Cita(
                id = 4L,
                createdAt = null,
                estado = "CONFIRMADA",
                fecha = "2026-05-05",
                motivo = "Sesión de apoyo emocional",
                notaProfesional = null,
                estudianteId = 2L, // Usamos un ID real de tu DB
                horarioId = 1L,
                profesionalId = 1L
            ),
            Cita(
                id = 5L,
                createdAt = null,
                estado = "PENDIENTE",
                fecha = "2026-05-05",
                motivo = "Revisión de plan de estudios",
                notaProfesional = null,
                estudianteId = 2L,
                horarioId = 2L,
                profesionalId = 1L
            )
        )

        rvAgenda.adapter = CitaAdapter(citasFalsas, esProfesional = true) { citaSeleccionada ->
            val fragmentoDestino = RegistrarSeguimientoFragment()
            fragmentoDestino.arguments = Bundle().apply {
                // CORRECCIÓN AQUÍ: Agregamos el Elvis operator ?: -1L
                putLong("ESTUDIANTE_ID", citaSeleccionada.estudianteId ?: -1L)
                putLong("CITA_ID", citaSeleccionada.id ?: -1L)
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.detail_container_prof, fragmentoDestino)
                .addToBackStack(null)
                .commit()
        }
    }
}