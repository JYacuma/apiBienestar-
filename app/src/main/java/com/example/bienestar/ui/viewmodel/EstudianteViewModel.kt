package com.example.bienestar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestar.model.Cita
import com.example.bienestar.model.Solicitud
import com.example.bienestar.repository.EstudianteRepository
import kotlinx.coroutines.launch

class EstudianteViewModel : ViewModel() {
    private val repository = EstudianteRepository()

    // Listas que la pantalla del estudiante va a mostrar
    val solicitudes = MutableLiveData<List<Solicitud>>()
    val citas = MutableLiveData<List<Cita>>()

    fun cargarDatos(estudianteId: Long) {
        viewModelScope.launch {
            try {
                val resSol = repository.getSolicitudes(estudianteId)
                val resCita = repository.getCitas(estudianteId)

                if (resSol.isSuccessful) solicitudes.postValue(resSol.body())
                if (resCita.isSuccessful) citas.postValue(resCita.body())
            } catch (e: Exception) {
                // Aquí más adelante pondremos mensajes de error
            }
        }
    }
}