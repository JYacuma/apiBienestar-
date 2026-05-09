package com.example.bienestar.ui.profesional

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bienestar.R
import com.example.bienestar.network.ApiClient
import com.example.bienestar.ui.adapters.CitaAdapter
import com.example.bienestar.utils.SessionManager
import kotlinx.coroutines.launch

class AgendaProfesionalFragment : Fragment(R.layout.fragment_agenda_profesional) {

    private lateinit var adapter: CitaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(requireContext())
        val idProf = sessionManager.obtenerIdUsuario() // Obtiene el ID 3

        val rvAgenda = view.findViewById<RecyclerView>(R.id.rvAgendaProf)
        rvAgenda.layoutManager = LinearLayoutManager(requireContext())

        // Configuramos el Adapter para profesionales
        adapter = CitaAdapter(emptyList(), esProfesional = true) { citaSeleccionada ->
            val fragmentoDestino = RegistrarSeguimientoFragment()
            fragmentoDestino.arguments = Bundle().apply {
                putLong("CITA_ID", citaSeleccionada.id ?: -1L)
                putLong("ESTUDIANTE_ID", citaSeleccionada.estudianteId ?: -1L)
                putString("NOMBRE_PACIENTE", citaSeleccionada.nombreEstudiante ?: "Juan Perez")
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.detail_container_prof, fragmentoDestino)
                .addToBackStack(null)
                .commit()
        }
        rvAgenda.adapter = adapter

        // 🚀 CARGAMOS LOS DATOS REALES DE RENDER
        cargarCitasReal(idProf)
    }

    private fun cargarCitasReal(id: Long) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getCitasProfesional(id)
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    adapter.updateList(lista)
                } else {
                    Toast.makeText(requireContext(), "Error al cargar agenda", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "⚠️ Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }
}