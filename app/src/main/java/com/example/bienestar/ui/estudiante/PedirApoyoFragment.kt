package com.example.bienestar.ui.estudiante

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bienestar.R

class PedirApoyoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedir_apoyo, container, false)

        val spinnerTipo = view.findViewById<Spinner>(R.id.spinnerTipoApoyo)
        val etDetalles = view.findViewById<EditText>(R.id.etDetallesApoyo)
        val btnEnviar = view.findViewById<Button>(R.id.btnEnviarSolicitud)

        // 1. Llenar el menú desplegable con las 3 opciones de tu documento
        val tiposDeApoyo = arrayOf("ACADEMICA", "EMOCIONAL", "SOCIAL")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, tiposDeApoyo)
        spinnerTipo.adapter = adapter

        // 2. Acción del botón enviar
        btnEnviar.setOnClickListener {
            val tipoSeleccionado = spinnerTipo.selectedItem.toString()
            val detalles = etDetalles.text.toString().trim()

            // Aquí a futuro llamaremos a tu ViewModel para enviarlo a Spring Boot
            // Por ahora, mostramos un mensaje de éxito simulado:
            Toast.makeText(
                requireContext(),
                "¡Solicitud $tipoSeleccionado enviada exitosamente!",
                Toast.LENGTH_LONG
            ).show()

            // Limpiar la caja de texto después de enviar
            etDetalles.text.clear()
        }

        return view
    }
}