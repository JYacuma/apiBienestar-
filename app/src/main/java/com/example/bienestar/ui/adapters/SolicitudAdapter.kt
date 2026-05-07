package com.example.bienestar.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bienestar.R
import com.example.bienestar.model.Solicitud

class SolicitudAdapter(
    private var solicitudes: List<Solicitud>
) : RecyclerView.Adapter<SolicitudAdapter.SolicitudViewHolder>() {

    class SolicitudViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTipo: TextView = view.findViewById(R.id.tvTipoSolicitud)
        val tvDesc: TextView = view.findViewById(R.id.tvDescripcionSolicitud)
        val tvEstado: TextView = view.findViewById(R.id.tvEstadoSolicitud)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolicitudViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_solicitud, parent, false)
        return SolicitudViewHolder(view)
    }

    override fun onBindViewHolder(holder: SolicitudViewHolder, position: Int) {
        val solicitud = solicitudes[position]

        // Mostramos el tipo de apoyo (Psicología, Académico, etc.)
        holder.tvTipo.text = solicitud.tipo

        // CORRECCIÓN: Como no hay 'descripcion' en Supabase,
        // usamos 'createdAt' para mostrar cuándo se hizo la solicitud.
        holder.tvDesc.text = "Registrada el: ${solicitud.createdAt ?: "Recién creada"}"

        // Mostramos el estado (PENDIENTE, PROCESADA, etc.)
        holder.tvEstado.text = solicitud.estado

        // Toque de diseño: Color según el estado
        if (solicitud.estado == "PENDIENTE") {
            holder.tvEstado.setTextColor(Color.parseColor("#F57C00")) // Naranja
        } else {
            holder.tvEstado.setTextColor(Color.parseColor("#2E7D32")) // Verde
        }
    }

    override fun getItemCount() = solicitudes.size

    fun updateList(nuevaLista: List<Solicitud>) {
        this.solicitudes = nuevaLista
        notifyDataSetChanged()
    }
}