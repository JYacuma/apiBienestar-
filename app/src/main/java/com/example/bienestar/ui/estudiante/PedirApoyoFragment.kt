package com.example.bienestar.ui.estudiante

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
// 🎯 USAMOS EL IMPORT DE LA CLASE BASE (Menos propenso a errores de referencia)
import androidx.navigation.Navigation
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

        // Configuración del Spinner
        val opcionesApoyo = arrayOf(
            "Apoyo Psicológico", "Apoyo Académico", "Apoyo Financiero",
            "Orientación Vocacional", "Atención Médica Básica"
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, opcionesApoyo)
        spinnerTipoApoyo.setAdapter(adapter)

        // Observador del resultado
        viewModel.solicitudResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Toast.makeText(requireContext(), "✅ Solicitud enviada", Toast.LENGTH_SHORT).show()

                // 🎯 FORMA MANUAL (La que nunca falla en compilación)
                try {
                    val navController = Navigation.findNavController(requireView())
                    navController.popBackStack()
                } catch (e: Exception) {
                    // Si todo lo anterior falla, el FragmentManager siempre está ahí
                    parentFragmentManager.popBackStack()
                }
            }
            result.onFailure { error ->
                btnEnviar.isEnabled = true
                btnEnviar.text = "Enviar Solicitud"
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

            btnEnviar.isEnabled = false
            btnEnviar.text = "ENVIANDO..."

            val tipoParaBackend = tipoSeleccionado.uppercase()
                .replace(" ", "_").replace("Á", "A").replace("É", "E")
                .replace("Í", "I").replace("Ó", "O").replace("Ú", "U")

            val bodySolicitud = hashMapOf<String, Any>(
                "tipo" to tipoParaBackend,
                "descripcion" to detalles
            )

            viewModel.pedirApoyo(1L, bodySolicitud)
        }
    }
}