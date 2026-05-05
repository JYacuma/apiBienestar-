package com.example.bienestar.ui.estudiante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bienestar.R
import com.example.bienestar.model.Cita
import com.example.bienestar.model.EstadoCita

// 1. El Adaptador (El puente entre la Tarjeta XML y los Datos)
class CitaAdapter(private val citas: List<Cita>) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    class CitaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFechaHora: TextView = view.findViewById(R.id.tvFechaHora)
        val tvProfesional: TextView = view.findViewById(R.id.tvProfesional)
        val tvEstado: TextView = view.findViewById(R.id.tvEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        // ¡LA LÍNEA CORREGIDA GRACIAS A TI! Es R.layout.item_cita
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]
        holder.tvFechaHora.text = "${cita.fecha} - ${cita.hora}"
        holder.tvProfesional.text = "ID Profesional: ${cita.profesionalId}"
        holder.tvEstado.text = cita.estado.name
    }

    override fun getItemCount() = citas.size
}

// 2. El Fragmento
class MisCitasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mis_citas, container, false)

        val rvMisCitas = view.findViewById<RecyclerView>(R.id.rvMisCitas)
        rvMisCitas.layoutManager = LinearLayoutManager(requireContext())

        // 3. Crear datos simulados (Bypass del Backend temporal)
        val citasFalsas = listOf(
            Cita(1L, 1L, 101L, "2026-05-10", "10:00:00", EstadoCita.CONFIRMADA, "Cita inicial"),
            Cita(2L, 1L, 102L, "2026-05-15", "14:30:00", EstadoCita.PENDIENTE, "Revisión académica"),
            Cita(3L, 1L, 101L, "2026-05-20", "09:00:00", EstadoCita.CANCELADA, "No podré asistir")
        )

        // 4. Conectar los datos con la lista
        rvMisCitas.adapter = CitaAdapter(citasFalsas)

        return view
    }
}