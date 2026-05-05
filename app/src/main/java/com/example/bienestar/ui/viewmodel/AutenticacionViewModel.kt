package com.example.bienestar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestar.model.Usuario
import com.example.bienestar.repository.UsuarioRepository
import kotlinx.coroutines.launch

class AutenticacionViewModel : ViewModel() {
    private val repository = UsuarioRepository()

    // Variables que las Activities van a observar
    val authResult = MutableLiveData<Result<Usuario>>()

    fun login(correo: String, contra: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(correo, contra)
                if (response.isSuccessful && response.body() != null) {
                    authResult.postValue(Result.success(response.body()!!))
                } else {
                    authResult.postValue(Result.failure(Exception("Error en Login. Revisa tus datos.")))
                }
            } catch (e: Exception) {
                authResult.postValue(Result.failure(Exception("Error de conexión con el servidor")))
            }
        }
    }
}