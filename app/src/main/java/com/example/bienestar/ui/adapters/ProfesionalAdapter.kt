package com.example.bienestar.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bienestar.R
import com.example.bienestar.model.Profesional

class ProfesionalAdapter(private val profesionales: List<Profesional>) : RecyclerView.Adapter<ProfesionalAdapter.ProfesionalViewHolder>() {

    class ProfesionalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreUsuario)
        val tvCorreo: TextView = view.findViewById(R.id.tvCorreoUsuario)
        val tvEspecialidad: TextView = view.findViewById(R.id.tvRolUsuario) // Reciclamos este textview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesionalViewHolder {
        // Usamos la misma tarjeta que diseñaste antes
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        return ProfesionalViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfesionalViewHolder, position: Int) {
        val prof = profesionales[position]
        holder.tvNombre.text = prof.nombre
        holder.tvCorreo.text = prof.correo
        holder.tvEspecialidad.text = "Especialidad: ${prof.especialidad}"
    }

    override fun getItemCount() = profesionales.size
}