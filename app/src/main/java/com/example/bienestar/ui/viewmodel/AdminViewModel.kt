package com.example.bienestar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestar.model.Profesional
import com.example.bienestar.repository.AdminRepository
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val repository = AdminRepository()

    val profesionales = MutableLiveData<List<Profesional>>()

    fun cargarProfesionales() {
        viewModelScope.launch {
            try {
                val response = repository.listarProfesionales()
                if (response.isSuccessful) profesionales.postValue(response.body())
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }
}