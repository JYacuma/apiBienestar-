package com.example.bienestar.ui.profesional

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bienestar.R
import com.example.bienestar.ui.adapters.ProfesionalViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfesionalMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesional_main)

        val viewPager = findViewById<ViewPager2>(R.id.viewPagerProf)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation_prof)

        // 1. Configuramos el adaptador especializado para el Profesional
        val adapter = ProfesionalViewPagerAdapter(this)
        viewPager.adapter = adapter

        // 2. Sincronizar: Deslizar pantalla -> Cambiar icono abajo
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNav.menu.getItem(position).isChecked = true
            }
        })

        // 3. Sincronizar: Tocar icono abajo -> Mover pantalla
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio_prof -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.nav_agenda_prof -> {
                    viewPager.currentItem = 1
                    true
                }
                else -> false
            }
        }

        // Mantenemos 2 páginas en memoria para que el cambio sea instantáneo
        viewPager.offscreenPageLimit = 1
    }
}