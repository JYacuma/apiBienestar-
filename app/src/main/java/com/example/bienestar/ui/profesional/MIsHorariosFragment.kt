package com.example.bienestar.ui.profesional

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bienestar.R
import com.example.bienestar.network.RetrofitClient // Recomendado usar RetrofitClient para timeouts
import com.example.bienestar.utils.SessionManager // 🎯 Importante
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MisHorariosFragment : Fragment(R.layout.fragment_mis_horarios) {

    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        val spinnerDia = view.findViewById<Spinner>(R.id.spinnerDia)
        val etHoraInicio = view.findViewById<EditText>(R.id.etHoraInicio)
        val etHoraFin = view.findViewById<EditText>(R.id.etHoraFin)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarHorario)

        val dias = arrayOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner_seleccionado, dias)
        adapter.setDropDownViewResource(R.layout.item_spinner_texto)
        spinnerDia.adapter = adapter

        btnGuardar.setOnClickListener {
            val hInicio = etHoraInicio.text.toString().trim()
            val hFin = etHoraFin.text.toString().trim()

            if (hInicio.isEmpty() || hFin.isEmpty()) {
                Toast.makeText(requireContext(), "⚠️ Ingresa las horas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnGuardar.isEnabled = false
            btnGuardar.text = "Guardando..."

            // 🎯 RECUPERAMOS EL ID REAL DE QUIEN ESTÁ LOGUEADO
            val usuarioId = sessionManager.obtenerIdUsuario()

            val body = mapOf(
                "dia" to spinnerDia.selectedItem.toString(),
                "horaInicio" to hInicio,
                "horaFin" to hFin,
                "activo" to true
            )

            enviarHorarioAlServidor(usuarioId, body, btnGuardar, etHoraInicio, etHoraFin)
        }
    }

    private fun enviarHorarioAlServidor(
        id: Long,
        datos: Map<String, Any>,
        boton: Button,
        campoInicio: EditText,
        campoFin: EditText
    ) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                // 🎯 LLAMAMOS A LA RUTA DEL PROFESIONAL PASANDO SU ID
                val response = RetrofitClient.apiService.crearHorarioProfesional(id, datos)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "✅ Horario guardado", Toast.LENGTH_SHORT).show()
                        campoInicio.text.clear()
                        campoFin.text.clear()
                    } else {
                        Toast.makeText(requireContext(), "❌ Error servidor", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "⚠️ Error de conexión", Toast.LENGTH_SHORT).show()
                }
            } finally {
                withContext(Dispatchers.Main) {
                    boton.isEnabled = true
                    boton.text = "GUARDAR HORARIO"
                }
            }
        }
    }
}