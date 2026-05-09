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

        val adapter = ProfesionalViewPagerAdapter(this)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNav.menu.getItem(position).isChecked = true
            }
        })

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
                // 🎯 NUEVO: Navegación a horarios
                R.id.nav_horarios_prof -> {
                    viewPager.currentItem = 2
                    true
                }
                else -> false
            }
        }

        viewPager.offscreenPageLimit = 2 // Subimos a 2 para mantener las 3 páginas listas
    }
}