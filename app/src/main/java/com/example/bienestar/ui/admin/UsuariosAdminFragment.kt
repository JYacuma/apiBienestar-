package com.example.bienestar.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bienestar.R
import com.example.bienestar.model.Rol
import com.example.bienestar.model.Usuario
import com.example.bienestar.ui.adapters.UsuarioAdapter

class UsuariosAdminFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_usuarios_admin, container, false)

        val rvUsuarios = view.findViewById<RecyclerView>(R.id.rvUsuariosAdmin)
        rvUsuarios.layoutManager = LinearLayoutManager(requireContext())

        // Datos simulados (Bypass del backend)
        val usuariosFalsos = listOf(
            Usuario(1L, "Juan Pérez", "juan@ejemplo.com", "1234", Rol.ESTUDIANTE, "Ing. Sistemas"),
            Usuario(2L, "Dra. Ana Gómez", "ana@ejemplo.com", "1234", Rol.PROFESIONAL, "Psicología"),
            Usuario(3L, "Carlos López", "carlos@ejemplo.com", "1234", Rol.ESTUDIANTE, "Enfermería")
        )

        rvUsuarios.adapter = UsuarioAdapter(usuariosFalsos)

        return view
    }
}