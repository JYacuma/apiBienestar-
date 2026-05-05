package com.example.bienestar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestar.model.Usuario
import com.example.bienestar.network.RetrofitClient
import kotlinx.coroutines.launch

class AutenticacionViewModel : ViewModel() {

    // Este LiveData es el que la LoginActivity estará "escuchando"
    private val _authResult = MutableLiveData<Result<Usuario>>()
    val authResult: LiveData<Result<Usuario>> = _authResult

    fun login(correo: String, contrasena: String) {
        // Ejecutamos en una corrutina para no bloquear la interfaz de usuario
        viewModelScope.launch {
            try {
                // AQUÍ ESTÁ LA CORRECCIÓN:
                // Las claves ahora están en español para que Spring Boot las reconozca.
                val credenciales = mapOf(
                    "correo" to correo,
                    "contrasena" to contrasena
                )

                // Llamamos a tu función de loginUsuario
                val respuesta = RetrofitClient.apiService.loginUsuario(credenciales)

                if (respuesta.isSuccessful && respuesta.body() != null) {
                    // ¡ÉXITO! Pasamos el objeto Usuario completo
                    _authResult.value = Result.success(respuesta.body()!!)
                } else {
                    // El servidor respondió con error (ej: 400, 401 o 404)
                    _authResult.value = Result.failure(Exception("Correo o contraseña incorrectos"))
                }
            } catch (e: Exception) {
                // Error de red (sin internet, servidor caído, timeout, etc.)
                _authResult.value = Result.failure(Exception("Error de conexión: Verifica tu internet"))
            }
        }
    }
}