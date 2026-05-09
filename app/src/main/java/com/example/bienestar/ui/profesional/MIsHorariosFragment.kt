package com.example.bienestar.ui.profesional

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bienestar.R
import com.example.bienestar.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MisHorariosFragment : Fragment(R.layout.fragment_mis_horarios) {

    // 🎯 ID del profesional (lo ideal es recuperarlo del Login, por ahora 1L)
    private val profesionalId: Long = 1L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerDia = view.findViewById<Spinner>(R.id.spinnerDia)
        val etHoraInicio = view.findViewById<EditText>(R.id.etHoraInicio)
        val etHoraFin = view.findViewById<EditText>(R.id.etHoraFin)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarHorario)

        // --- 1. CONFIGURACIÓN DEL SELECTOR DE DÍAS ---
        val dias = arrayOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado")

        // 🎯 CORRECCIÓN VISUAL: Usamos 'item_spinner_seleccionado' para que el texto sea oscuro
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner_seleccionado, dias)

        // 🎯 Para el menú desplegable usamos el diseño oscuro con letra blanca
        adapter.setDropDownViewResource(R.layout.item_spinner_texto)
        spinnerDia.adapter = adapter

        // --- 2. LÓGICA DEL BOTÓN GUARDAR ---
        btnGuardar.setOnClickListener {
            val hInicio = etHoraInicio.text.toString().trim()
            val hFin = etHoraFin.text.toString().trim()

            if (hInicio.isEmpty() || hFin.isEmpty()) {
                Toast.makeText(requireContext(), "⚠️ Por favor, ingresa las horas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🔒 Bloqueamos el botón mientras se procesa en la nube
            btnGuardar.isEnabled = false
            btnGuardar.text = "Guardando..."

            val body = mapOf(
                "profesionalId" to profesionalId,
                "dia" to spinnerDia.selectedItem.toString(),
                "horaInicio" to hInicio,
                "horaFin" to hFin,
                "activo" to true
            )

            enviarHorarioAlServidor(body, btnGuardar, etHoraInicio, etHoraFin)
        }
    }

    private fun enviarHorarioAlServidor(
        datos: Map<String, Any>,
        boton: Button,
        campoInicio: EditText,
        campoFin: EditText
    ) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = ApiClient.apiService.crearHorario(datos)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "✅ Horario creado correctamente", Toast.LENGTH_SHORT).show()
                        // Limpiamos los campos para el siguiente registro
                        campoInicio.text.clear()
                        campoFin.text.clear()
                    } else {
                        Toast.makeText(requireContext(), "❌ Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API_ERROR", "Fallo: ${e.message}")
                    Toast.makeText(requireContext(), "⚠️ Error de conexión con Render", Toast.LENGTH_SHORT).show()
                }
            } finally {
                // 🔓 Siempre desbloqueamos el botón al final, pase lo que pase
                withContext(Dispatchers.Main) {
                    boton.isEnabled = true
                    boton.text = "GUARDAR HORARIO"
                }
            }
        }
    }
}