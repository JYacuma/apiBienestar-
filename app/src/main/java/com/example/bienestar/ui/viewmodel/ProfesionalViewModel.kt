package com.example.bienestar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestar.model.Cita
import com.example.bienestar.repository.ProfesionalRepository
import kotlinx.coroutines.launch

class ProfesionalViewModel : ViewModel() {
    private val repository = ProfesionalRepository()

    val citasHoy = MutableLiveData<List<Cita>>()

    fun cargarCitas(profesionalId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getCitas(profesionalId)
                if (response.isSuccessful) citasHoy.postValue(response.body())
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }
}