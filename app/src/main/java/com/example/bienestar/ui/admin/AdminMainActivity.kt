package com.example.bienestar.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bienestar.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation_admin)

        if (savedInstanceState == null) {
            cargarFragmento(InicioAdminFragment())
            bottomNav.selectedItemId = R.id.nav_inicio_admin
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio_admin -> {
                    cargarFragmento(InicioAdminFragment())
                    true
                }
                R.id.nav_usuarios -> {
                    cargarFragmento(UsuariosAdminFragment())
                    true
                }
                R.id.nav_horarios -> {
                    cargarFragmento(HorariosAdminFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun cargarFragmento(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_admin, fragment)
            .commit()
    }
}