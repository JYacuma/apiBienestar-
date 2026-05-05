package com.example.bienestar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestar.model.Profesional
import com.example.bienestar.repository.AdminRepository
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {

    // Instanciamos el repositorio que creaste en el paso anterior
    private val repository = AdminRepository()

    // Aquí guardamos la lista de profesionales para que la pantalla la observe
    private val _profesionalesResult = MutableLiveData<Result<List<Profesional>>>()
    val profesionalesResult: LiveData<Result<List<Profesional>>> = _profesionalesResult

    // Función que la pantalla llamará cuando quiera cargar los datos
    fun cargarProfesionales() {
        viewModelScope.launch {
            // El repositorio va a internet y nos devuelve el éxito o el error
            val resultado = repository.obtenerProfesionales()
            _profesionalesResult.value = resultado
        }
    }
}