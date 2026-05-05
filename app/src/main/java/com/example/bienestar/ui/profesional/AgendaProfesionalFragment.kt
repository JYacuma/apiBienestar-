package com.example.bienestar.ui.profesional

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bienestar.R
import com.example.bienestar.model.Cita
import com.example.bienestar.model.EstadoCita
import com.example.bienestar.ui.adapters.CitaAdapter

class AgendaProfesionalFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agenda_profesional, container, false)

        val rvAgenda = view.findViewById<RecyclerView>(R.id.rvAgendaProf)
        rvAgenda.layoutManager = LinearLayoutManager(requireContext())

        val citasFalsas = listOf(
            Cita(4L, 201L, 2L, "2026-05-05", "08:00:00", EstadoCita.CONFIRMADA, "Sesión de apoyo emocional"),
            Cita(5L, 205L, 2L, "2026-05-05", "11:00:00", EstadoCita.PENDIENTE, "Revisión de plan de estudios")
        )

        // Aquí le decimos al adaptador: "Cuando toquen una cita, abre la pantalla de seguimiento"
        rvAgenda.adapter = CitaAdapter(citasFalsas) { citaSeleccionada ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_prof, RegistrarSeguimientoFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}