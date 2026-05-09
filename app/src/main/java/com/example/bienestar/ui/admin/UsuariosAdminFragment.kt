package com.example.bienestar.ui.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bienestar.R
import com.example.bienestar.network.ApiClient
import com.example.bienestar.ui.adapters.UsuarioAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuariosAdminFragment : Fragment() {

    private lateinit var rvUsuarios: RecyclerView
    private lateinit var adapter: UsuarioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_usuarios_admin, container, false)

        rvUsuarios = view.findViewById(R.id.rvUsuariosAdmin)
        rvUsuarios.layoutManager = LinearLayoutManager(requireContext())

        // Iniciamos con una lista vacía mientras carga
        adapter = UsuarioAdapter(emptyList())
        rvUsuarios.adapter = adapter

        // Llamamos a la base de datos real
        cargarUsuariosReales()

        return view
    }

    private fun cargarUsuariosReales() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Hacemos la petición al backend
                val response = ApiClient.apiService.getTodosLosUsuarios()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val usuariosReales = response.body()!!
                        // Actualizamos el RecyclerView con los datos de Supabase
                        rvUsuarios.adapter = UsuarioAdapter(usuariosReales)
                    } else {
                        Toast.makeText(requireContext(), "Error al cargar usuarios", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Sin conexión al servidor", Toast.LENGTH_SHORT).show()
                    Log.e("ADMIN_USUARIOS", "Error: ${e.message}")
                }
            }
        }
    }
}