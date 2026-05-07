package com.example.bienestar.ui.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bienestar.R
import com.example.bienestar.network.ApiClient
import com.example.bienestar.ui.autenticacion.LoginActivity
import com.example.bienestar.utils.SessionManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class InicioAdminFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_inicio_admin, container, false)
        val sessionManager = SessionManager(requireContext())

        // IDs de los TextViews (Verifica que coincidan con tu XML)
        val tvCitasHoy = view.findViewById<TextView>(R.id.tvCitasHoy)
        val tvSolicitudes = view.findViewById<TextView>(R.id.tvSolicitudesPendientes)
        val tvBienvenida = view.findViewById<TextView>(R.id.tvBienvenidaInicio)

        tvBienvenida.text = "¡Hola, Jefe!\nPanel de Administración"

        // Llamada a la API para cargar estadísticas
        cargarEstadisticas(tvCitasHoy, tvSolicitudes)

        view.findViewById<Button>(R.id.btnCerrarSesion).setOnClickListener {
            sessionManager.cerrarSesion()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }

        return view
    }

    private fun cargarEstadisticas(tvCitas: TextView, tvSolis: TextView) {
        lifecycleScope.launch {
            try {
                // 1. Obtener y filtrar citas de HOY
                val respCitas = ApiClient.apiService.getTodasLasCitas()
                if (respCitas.isSuccessful) {
                    val todas = respCitas.body() ?: emptyList()

                    // Obtenemos la fecha actual en formato YYYY-MM-DD
                    val hoy = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                    // Filtramos las que tengan la fecha de hoy
                    val conteoHoy = todas.filter { it.fecha == hoy }.size
                    tvCitas.text = conteoHoy.toString()
                }

                // 2. Obtener solicitudes pendientes
                val respSoli = ApiClient.apiService.getTodasLasSolicitudes()
                if (respSoli.isSuccessful) {
                    val conteoSolis = respSoli.body()?.size ?: 0
                    tvSolis.text = conteoSolis.toString()
                }

            } catch (e: Exception) {
                Log.e("ADMIN_DEBUG", "Error al cargar dashboard: ${e.message}")
            }
        }
    }
}