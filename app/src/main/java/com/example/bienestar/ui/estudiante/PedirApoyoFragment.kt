package com.example.bienestar.ui.estudiante

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bienestar.R
import com.example.bienestar.viewmodel.EstudianteViewModel
import com.google.android.material.textfield.TextInputEditText

class PedirApoyoFragment : Fragment(R.layout.fragment_pedir_apoyo) {

    private lateinit var viewModel: EstudianteViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(EstudianteViewModel::class.java)

        val spinnerTipoApoyo = view.findViewById<AutoCompleteTextView>(R.id.spinnerTipoApoyo)
        val etDetalles = view.findViewById<TextInputEditText>(R.id.etDetallesApoyo)
        val btnEnviar = view.findViewById<Button>(R.id.btnEnviarSolicitud)

        val opcionesApoyo = arrayOf(
            "Apoyo Psicológico",
            "Apoyo Académico",
            "Apoyo Financiero",
            "Orientación Vocacional",
            "Atención Médica Básica"
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, opcionesApoyo)
        spinnerTipoApoyo.setAdapter(adapter)

        viewModel.solicitudResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Toast.makeText(requireContext(), "✅ Solicitud enviada con éxito", Toast.LENGTH_LONG).show()
                parentFragmentManager.popBackStack()
            }
            result.onFailure { error ->
                Toast.makeText(requireContext(), "❌ Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }

        btnEnviar.setOnClickListener {
            val tipoSeleccionado = spinnerTipoApoyo.text.toString()
            val detalles = etDetalles.text.toString().trim()

            if (tipoSeleccionado.isEmpty() || detalles.isEmpty()) {
                Toast.makeText(requireContext(), "⚠️ Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // --- AQUÍ ESTÁ EL "3" (NORMALIZACIÓN) ---
            // Pasamos de "Apoyo Psicológico" a "APOYO_PSICOLOGICO"
            val tipoParaBackend = tipoSeleccionado.uppercase()
                .replace(" ", "_")
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U")

            val bodySolicitud = hashMapOf<String, Any>(
                "tipo" to tipoParaBackend,
                "descripcion" to detalles
            )

            viewModel.pedirApoyo(1L, bodySolicitud)
        }
    }
}