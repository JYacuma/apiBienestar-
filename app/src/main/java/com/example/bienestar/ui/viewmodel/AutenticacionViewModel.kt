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

    // 🎯 FUNCIÓN NUEVA PARA ESTUDIANTES
    fun registrarEstudiante(datos: Map<String, String>) {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitClient.apiService.registrarEstudiante(datos)
                if (respuesta.isSuccessful && respuesta.body() != null) {
                    _registroResult.value = Result.success(respuesta.body()!!)
                } else {
                    _registroResult.value = Result.failure(Exception("Error al registrar estudiante"))
                }
            } catch (e: Exception) {
                _registroResult.value = Result.failure(Exception("Error de red: ${e.message}"))
            }
        }
    }

    // 🎯 FUNCIÓN NUEVA PARA PROFESIONALES
    fun registrarProfesional(datos: Map<String, String>) {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitClient.apiService.registrarProfesional(datos)
                if (respuesta.isSuccessful && respuesta.body() != null) {
                    _registroResult.value = Result.success(respuesta.body()!!)
                } else {
                    _registroResult.value = Result.failure(Exception("Error al registrar profesional"))
                }
            } catch (e: Exception) {
                _registroResult.value = Result.failure(Exception("Error de red: ${e.message}"))
            }
        }
    }
}