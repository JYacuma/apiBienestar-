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
                // 🎯 Ahora el repo usará el ID dinámico para las citas
                val resCita = repository.getCitas(estudianteId)

                if (resSol.isSuccessful) solicitudes.postValue(resSol.body())
                if (resCita.isSuccessful) citas.postValue(resCita.body())
            } catch (e: Exception) {
                // Manejo de error
            }
        }
    }

    // 🎯 Agregamos estudianteId como parámetro para que no sea fijo
    fun agendarCita(estudianteId: Long, citaData: Map<String, Any>) {
        viewModelScope.launch {
            try {
                // 🎯 Llamamos al repo pasando el ID dinámico
                val respuesta = repository.agendarCita(estudianteId, citaData)

                if (respuesta.isSuccessful && respuesta.body() != null) {
                    _agendarResult.value = Result.success(respuesta.body()!!)
                    // 🎯 RECARGAMOS CON EL ID REAL, NO CON 1L
                    cargarDatos(estudianteId)
                } else {
                    _agendarResult.value = Result.failure(Exception("Error al guardar la cita"))
                }
            } catch (e: Exception) {
                _agendarResult.value = Result.failure(Exception("Error de conexión: ${e.message}"))
            }
        }
    }

    fun pedirApoyo(estudianteId: Long, solicitudData: Map<String, Any>) {
        viewModelScope.launch {
            try {
                val respuesta = repository.pedirApoyo(estudianteId, solicitudData)
                if (respuesta.isSuccessful && respuesta.body() != null) {
                    _solicitudResult.value = Result.success(respuesta.body()!!)
                    cargarDatos(estudianteId)
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