package com.example.bienestar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestar.model.Cita
import com.example.bienestar.repository.ProfesionalRepository
import kotlinx.coroutines.launch

class ProfesionalViewModel : ViewModel() {

    // Instanciamos el repositorio que ya habías creado
    private val repository = ProfesionalRepository()

    // Aquí guardamos la lista de citas para que la pantalla la escuche
    private val _citasResult = MutableLiveData<Result<List<Cita>>>()
    val citasResult: LiveData<Result<List<Cita>>> = _citasResult

    // Función que llamará la pantalla pasándole el ID del profesional que inició sesión
    fun cargarCitas(profesionalId: Long) {
        viewModelScope.launch {
            // Vamos a internet a buscar las citas de este profesional específico
            val resultado = repository.obtenerCitasDelProfesional(profesionalId)
            _citasResult.value = resultado
        }
    }
}