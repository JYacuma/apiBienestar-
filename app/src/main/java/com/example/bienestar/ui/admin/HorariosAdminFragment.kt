package com.example.bienestar.ui.admin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bienestar.R
import com.example.bienestar.network.ApiClient
import com.example.bienestar.ui.adapters.CitaAdapter
import kotlinx.coroutines.launch

class HorariosAdminFragment : Fragment(R.layout.fragment_horarios_admin) {

    private lateinit var rvHorarios: RecyclerView
    private lateinit var adaptador: CitaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvHorarios = view.findViewById(R.id.rvHorariosAdmin)
        rvHorarios.layoutManager = LinearLayoutManager(requireContext())

        // 1. Iniciamos el adaptador vacío una ÚNICA vez
        adaptador = CitaAdapter(emptyList(), esProfesional = false)
        rvHorarios.adapter = adaptador

        // CARGAMOS DATOS REALES
        consultarCitasDesdeServidor()
    }

    private fun consultarCitasDesdeServidor() {
        lifecycleScope.launch {
            try {
                // Llamamos a la ruta del Admin que obtiene TODO
                val response = ApiClient.apiService.getTodasLasCitas()

                if (response.isSuccessful && response.body() != null) {
                    val listaCitasReal = response.body()!!

                    // CORRECCIÓN CLAVE: No creamos el adaptador de nuevo, solo actualizamos su lista
                    adaptador.updateList(listaCitasReal)

                    if (listaCitasReal.isEmpty()) {
                        Toast.makeText(requireContext(), "No hay citas registradas aún", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error al obtener datos: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ADMIN_ERROR", "Falla de red: ${e.message}")
                Toast.makeText(requireContext(), "Error de conexión con el servidor", Toast.LENGTH_SHORT).show()
            }
        }
    }
}