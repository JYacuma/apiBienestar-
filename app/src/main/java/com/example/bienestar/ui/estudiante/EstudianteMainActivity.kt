package com.example.bienestar.ui.estudiante

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bienestar.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class EstudianteMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Que al iniciar la app cargue el Inicio por defecto
        if (savedInstanceState == null) {
            cargarFragmento(InicioEstudianteFragment())
            bottomNav.selectedItemId = R.id.nav_inicio // Marca el ícono de inicio como seleccionado
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    cargarFragmento(InicioEstudianteFragment())
                    true
                }
                R.id.nav_citas -> {
                    cargarFragmento(MisCitasFragment())
                    true
                }
                R.id.nav_apoyo -> {
                    cargarFragmento(PedirApoyoFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun cargarFragmento(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}