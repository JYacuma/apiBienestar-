package com.example.bienestar.ui.profesional

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bienestar.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfesionalMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesional_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation_prof)

        if (savedInstanceState == null) {
            cargarFragmento(InicioProfesionalFragment())
            bottomNav.selectedItemId = R.id.nav_inicio_prof
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio_prof -> {
                    cargarFragmento(InicioProfesionalFragment())
                    true
                }
                R.id.nav_agenda_prof -> {
                    cargarFragmento(AgendaProfesionalFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun cargarFragmento(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_prof, fragment)
            .commit()
    }
}