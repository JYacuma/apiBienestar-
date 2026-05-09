package com.example.bienestar.ui.autenticacion

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bienestar.R

class SeleccionRolFragment : Fragment(R.layout.fragment_seleccion_rol) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnSoyEstudiante).setOnClickListener {
            findNavController().navigate(R.id.action_seleccion_to_estudiante)
        }

        view.findViewById<Button>(R.id.btnSoyProfesional).setOnClickListener {
            findNavController().navigate(R.id.action_seleccion_to_profesional)
        }
    }
}