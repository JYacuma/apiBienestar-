package com.example.bienestar.ui.autenticacion

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bienestar.R
import com.example.bienestar.viewmodel.AutenticacionViewModel
import com.google.android.material.textfield.TextInputEditText

class RegistroProfesionalFragment : Fragment(R.layout.fragment_registro_profesional) {

    private lateinit var viewModel: AutenticacionViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AutenticacionViewModel::class.java)

        val etNombre = view.findViewById<TextInputEditText>(R.id.etNombreRegistro)
        val etCorreo = view.findViewById<TextInputEditText>(R.id.etCorreoRegistro)
        val etEspecialidad = view.findViewById<TextInputEditText>(R.id.etEspecialidadRegistro)
        val etContrasena = view.findViewById<TextInputEditText>(R.id.etContrasenaRegistro)
        val btnRegistrar = view.findViewById<Button>(R.id.btnRegistrar)

        viewModel.registroResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Toast.makeText(requireContext(), "¡Profesional registrado con éxito!", Toast.LENGTH_SHORT).show()

                // 🎯 Cerramos la actividad para volver atrás
                requireActivity().finish()
            }
            result.onFailure { error ->
                btnRegistrar.isEnabled = true
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val especialidad = etEspecialidad.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (nombre.isEmpty() || correo.isEmpty() || especialidad.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(requireContext(), "Llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnRegistrar.isEnabled = false
            val body = hashMapOf(
                "nombre" to nombre, "correo" to correo, "contrasena" to contrasena,
                "especialidad" to especialidad
            )
            viewModel.registrarProfesional(body)
        }
    }
}