package com.example.bienestar.ui.admin

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

class InicioAdminFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_inicio_estudiante, container, false)
        val sessionManager = SessionManager(requireContext())

        view.findViewById<TextView>(R.id.tvBienvenidaInicio).text = "¡Hola, Jefe!\nPanel de Administración"

        view.findViewById<Button>(R.id.btnCerrarSesion).setOnClickListener {
            sessionManager.cerrarSesion()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }
        return view
    }
}