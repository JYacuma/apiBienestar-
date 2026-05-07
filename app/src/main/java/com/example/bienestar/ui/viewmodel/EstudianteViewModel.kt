package com.example.bienestar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestar.model.Cita
import com.example.bienestar.model.Solicitud
import com.example.bienestar.repository.EstudianteRepository
import kotlinx.coroutines.launch

class EstudianteViewModel : ViewModel() {
    private val repository = EstudianteRepository()

    val solicitudes = MutableLiveData<List<Solicitud>>()
    val citas = MutableLiveData<List<Cita>>()

    private val _agendarResult = MutableLiveData<Result<Cita>>()
    val agendarResult: LiveData<Result<Cita>> = _agendarResult

    private val _solicitudResult = MutableLiveData<Result<Solicitud>>()
    val solicitudResult: LiveData<Result<Solicitud>> = _solicitudResult

    // ─── Funciones ───

    fun cargarDatos(estudianteId: Long) {
        viewModelScope.launch {
            try {
                val resSol = repository.getSolicitudes(estudianteId)
                // Aquí el repo ya sabe que no necesita el ID para las citas,
                // pero se lo pasamos por la firma de la función.
                val resCita = repository.getCitas(estudianteId)

                if (resSol.isSuccessful) solicitudes.postValue(resSol.body())
                if (resCita.isSuccessful) citas.postValue(resCita.body())
            } catch (e: Exception) {
                // Manejo de error
            }
        }
    }

    // CORREGIDO: Ahora recibe el Mapa que armamos en el Fragment
    fun agendarCita(citaData: Map<String, Any>) {
        viewModelScope.launch {
            try {
                // CORREGIDO: Llamada al repo con un solo parámetro
                val respuesta = repository.agendarCita(citaData)

                if (respuesta.isSuccessful && respuesta.body() != null) {
                    _agendarResult.value = Result.success(respuesta.body()!!)
                    // Recargamos con el ID fijo 1
                    cargarDatos(1L)
                } else {
                    _agendarResult.value = Result.failure(Exception("Error al guardar la cita"))
                }
            } catch (e: Exception) {
                _agendarResult.value = Result.failure(Exception("Error de conexión: ${e.message}"))
            }
        }
    }

    // En EstudianteViewModel.kt
    fun pedirApoyo(estudianteId: Long, solicitudData: Map<String, Any>) { // <--- CAMBIA ESTO
        viewModelScope.launch {
            try {
                val respuesta = repository.pedirApoyo(estudianteId, solicitudData)
                if (respuesta.isSuccessful && respuesta.body() != null) {
                    _solicitudResult.value = Result.success(respuesta.body()!!)
                    cargarDatos(estudianteId) // Refresca la lista automáticamente
                } else {
                    val errorMsg = respuesta.errorBody()?.string() ?: "Error desconocido"
                    _solicitudResult.value = Result.failure(Exception("Error $errorMsg"))
                }
            } catch (e: Exception) {
                _solicitudResult.value = Result.failure(e)
            }
        }
    }
}