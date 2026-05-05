package com.example.bienestar.ui.estudiante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bienestar.R

class AgendarCitaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agendar_cita, container, false)

        val spinner = view.findViewById<Spinner>(R.id.spinnerProfesionales)
        val btnConfirmar = view.findViewById<Button>(R.id.btnConfirmarCita)

        // Simulamos una lista de profesionales (luego vendrá de Spring Boot)
        val profesionales = arrayOf("Dr. Pérez (Psicología)", "Dra. Gómez (Académica)")
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, profesionales)

        btnConfirmar.setOnClickListener {
            Toast.makeText(requireContext(), "¡Cita agendada!", Toast.LENGTH_SHORT).show()

            // Esta línea mágica nos regresa a la pantalla anterior (Mis Citas)
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}