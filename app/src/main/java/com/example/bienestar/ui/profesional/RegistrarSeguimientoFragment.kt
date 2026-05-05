package com.example.bienestar.ui.profesional

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bienestar.R

class RegistrarSeguimientoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registrar_seguimiento, container, false)

        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarSeguimiento)

        btnGuardar.setOnClickListener {
            Toast.makeText(requireContext(), "¡Reporte guardado con éxito!", Toast.LENGTH_SHORT).show()
            // Nos devuelve a la agenda automáticamente
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}