package com.example.bienestar.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bienestar.R
import com.example.bienestar.model.Cita

// Le agregamos una variable opcional 'onCitaClick'
class CitaAdapter(
    private val citas: List<Cita>,
    private val onCitaClick: ((Cita) -> Unit)? = null
) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    class CitaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFechaHora: TextView = view.findViewById(R.id.tvFechaHora)
        val tvProfesional: TextView = view.findViewById(R.id.tvProfesional)
        val tvEstado: TextView = view.findViewById(R.id.tvEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]
        holder.tvFechaHora.text = "${cita.fecha} - ${cita.hora}"
        holder.tvProfesional.text = "ID Vinculado: ${if (cita.estudianteId != 0L) cita.estudianteId else cita.profesionalId}"
        holder.tvEstado.text = cita.estado.name

        // ¡Magia! Si pasamos una acción de clic, la ejecutamos aquí
        holder.itemView.setOnClickListener {
            onCitaClick?.invoke(cita)
        }
    }

    override fun getItemCount() = citas.size
}