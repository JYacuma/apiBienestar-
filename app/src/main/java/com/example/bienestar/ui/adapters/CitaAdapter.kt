package com.example.bienestar.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bienestar.R
import com.example.bienestar.model.Cita

class CitaAdapter(
    private var citas: List<Cita>,
    private val esProfesional: Boolean = false,
    private val onCitaClick: ((Cita) -> Unit)? = null
) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    class CitaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFechaHora: TextView = view.findViewById(R.id.tvFechaHora)
        val tvProfesional: TextView = view.findViewById(R.id.tvProfesional)
        // 🎯 NUEVO: Referencia al TextView del motivo
        val tvMotivo: TextView = view.findViewById(R.id.tvMotivo)
        val tvEstado: TextView = view.findViewById(R.id.tvEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]

        // 1. Fecha y Hora combinadas
        val fecha = cita.fecha ?: "Sin fecha"
        val hora = cita.horaInicio ?: "--:--"
        holder.tvFechaHora.text = "$fecha | $hora"

        // 2. Estado con colores
        val estadoActual = cita.estado ?: "PENDIENTE"
        holder.tvEstado.text = estadoActual.uppercase()

        when (estadoActual.uppercase()) {
            "PENDIENTE" -> holder.tvEstado.setTextColor(Color.parseColor("#F57C00"))
            "CONFIRMADA" -> holder.tvEstado.setTextColor(Color.parseColor("#2E7D32"))
            "CANCELADA" -> holder.tvEstado.setTextColor(Color.RED)
            else -> holder.tvEstado.setTextColor(Color.GRAY)
        }

        // 3. Mostrar Especialidad - Nombre Profesional o Estudiante
        if (esProfesional) {
            holder.tvProfesional.text = "Estudiante: ${cita.nombreEstudiante ?: "N/A"}"
        } else {
            val especialidad = cita.especialidad ?: "Bienestar"
            val nombreProf = cita.nombreProfesional ?: "Por asignar"
            holder.tvProfesional.text = "$especialidad - $nombreProf"
        }

        // 🎯 4. NUEVO: Asignar el motivo a la tarjeta
        val motivo = cita.motivo ?: "Sin motivo especificado"
        holder.tvMotivo.text = "Motivo: $motivo"

        holder.itemView.setOnClickListener {
            onCitaClick?.invoke(cita)
        }
    }

    override fun getItemCount() = citas.size

    fun updateList(nuevaLista: List<Cita>) {
        this.citas = nuevaLista
        notifyDataSetChanged()
    }
}