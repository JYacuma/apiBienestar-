package com.example.bienestar.ui.estudiante

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bienestar.R
import com.example.bienestar.ui.adapters.MainViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

// ... (tus imports iguales)

class EstudianteMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante_main)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val adapter = MainViewPagerAdapter(this)
        viewPager.adapter = adapter

        // 2. Sincronizar: ViewPager -> BottomNav
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                // El paracaídas: Solo intenta marcar si el índice existe en el menú
                if (position < bottomNav.menu.size()) {
                    bottomNav.menu.getItem(position).isChecked = true
                }
            }
        })

        // 3. Sincronizar: BottomNav -> ViewPager
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.nav_citas -> {
                    viewPager.currentItem = 1
                    true
                }
                R.id.nav_apoyo -> {
                    viewPager.currentItem = 2
                    true
                }
                else -> false
            }
        }

        // Importante: Si solo tienes 3 secciones, el límite debe ser 2
        viewPager.offscreenPageLimit = 2
    }
}