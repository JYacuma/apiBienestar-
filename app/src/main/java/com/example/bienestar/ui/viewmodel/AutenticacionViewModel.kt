package com.example.bienestar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestar.model.Usuario
import com.example.bienestar.network.RetrofitClient
import kotlinx.coroutines.launch

class AutenticacionViewModel : ViewModel() {

    private val _authResult = MutableLiveData<Result<Usuario>>()
    val authResult: LiveData<Result<Usuario>> = _authResult

    // Nuevo LiveData para observar el estado del registro
    private val _registroResult = MutableLiveData<Result<Usuario>>()
    val registroResult: LiveData<Result<Usuario>> = _registroResult

    fun login(correo: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val credenciales = mapOf("correo" to correo, "contrasena" to contrasena)
                val respuesta = RetrofitClient.apiService.loginUsuario(credenciales)
                if (respuesta.isSuccessful && respuesta.body() != null) {
                    _authResult.value = Result.success(respuesta.body()!!)
                } else {
                    _authResult.value = Result.failure(Exception("Correo o contraseña incorrectos"))
                }
            } catch (e: Exception) {
                _authResult.value = Result.failure(Exception("Error de conexión"))
            }
        }
    }

    // Función para registrar un nuevo usuario
    fun registrar(usuario: Usuario) {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitClient.apiService.registrarUsuario(usuario)
                if (respuesta.isSuccessful && respuesta.body() != null) {
                    _registroResult.value = Result.success(respuesta.body()!!)
                } else {
                    _registroResult.value = Result.failure(Exception("No se pudo completar el registro"))
                }
            } catch (e: Exception) {
                _registroResult.value = Result.failure(Exception("Error de red: ${e.message}"))
            }
        }
    }
}