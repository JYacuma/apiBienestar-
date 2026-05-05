package com.example.bienestar.ui.estudiante

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

// ESTA ES LA MAGIA: Importamos el adaptador desde la nueva carpeta neutral
import com.example.bienestar.ui.adapters.CitaAdapter

class MisCitasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mis_citas, container, false)

        val rvMisCitas = view.findViewById<RecyclerView>(R.id.rvMisCitas)
        rvMisCitas.layoutManager = LinearLayoutManager(requireContext())

        // Crear datos simulados (Bypass del Backend temporal)
        val citasFalsas = listOf(
            Cita(1L, 1L, 101L, "2026-05-10", "10:00:00", EstadoCita.CONFIRMADA, "Cita inicial"),
            Cita(2L, 1L, 102L, "2026-05-15", "14:30:00", EstadoCita.PENDIENTE, "Revisión académica"),
            Cita(3L, 1L, 101L, "2026-05-20", "09:00:00", EstadoCita.CANCELADA, "No podré asistir")
        )

        // Conectar los datos con la lista usando el adaptador neutral
        rvMisCitas.adapter = CitaAdapter(citasFalsas)

        val fabAgendar = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAgendarCita)
        fabAgendar.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AgendarCitaFragment())
                .addToBackStack(null) // Esto permite que el botón "Atrás" del celular funcione bien
                .commit()
        }

        return view
    }
}