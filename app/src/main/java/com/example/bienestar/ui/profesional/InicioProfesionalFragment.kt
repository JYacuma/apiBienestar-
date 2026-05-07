package com.example.bienestar.ui.profesional

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bienestar.R
import com.example.bienestar.ui.autenticacion.LoginActivity
import com.example.bienestar.utils.SessionManager

class InicioProfesionalFragment : Fragment(R.layout.fragment_inicio_profesional) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(requireContext())
        val tvBienvenida = view.findViewById<TextView>(R.id.tvBienvenidaProf)
        val tvDetallesCita = view.findViewById<TextView>(R.id.tvDetallesCitaProf)
        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesionProf)

        // Nombre real desde la sesión
        val nombre = sessionManager.getNombre() ?: "Profesional"
        tvBienvenida.text = "¡Hola, $nombre!"

        // Simulamos la carga del siguiente estudiante a atender
        val hayAtenciones = true
        if (hayAtenciones) {
            tvDetallesCita.text = "📅 Hoy a las: 08:00 AM\n👤 Estudiante ID: 201\n📋 Motivo: Sesión de apoyo emocional"
        } else {
            tvDetallesCita.text = "Tu agenda está libre en este momento. ¡Buen trabajo!"
        }

        btnCerrarSesion.setOnClickListener {
            sessionManager.cerrarSesion()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }
}