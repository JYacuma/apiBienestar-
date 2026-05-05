package com.example.bienestar.ui.estudiante

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bienestar.R
import com.example.bienestar.ui.autenticacion.LoginActivity
import com.example.bienestar.utils.SessionManager

class InicioEstudianteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio_estudiante, container, false)

        val sessionManager = SessionManager(requireContext())
        val tvBienvenida = view.findViewById<TextView>(R.id.tvBienvenidaInicio)
        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesion)

        // Recuperamos el nombre del usuario logueado
        tvBienvenida.text = "¡Hola, ${sessionManager.getNombre()}!\nPanel de Estudiante"

        // Lógica para cerrar sesión
        btnCerrarSesion.setOnClickListener {
            sessionManager.cerrarSesion()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }

        return view
    }
}