package com.example.bienestar.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bienestar.R
import com.example.bienestar.ui.adapters.AdminViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        val viewPager = findViewById<ViewPager2>(R.id.viewPagerAdmin)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation_admin)

        // 1. Configuramos el adaptador para los 3 paneles del Admin
        val adapter = AdminViewPagerAdapter(this)
        viewPager.adapter = adapter

        // 2. Sincronizar: Swipe con el dedo -> Actualiza el ícono del menú
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNav.menu.getItem(position).isChecked = true
            }
        })

        // 3. Sincronizar: Clic en el menú -> Mueve el ViewPager
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio_admin -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.nav_usuarios -> { // Usando tus IDs exactos
                    viewPager.currentItem = 1
                    true
                }
                R.id.nav_horarios -> { // Usando tus IDs exactos
                    viewPager.currentItem = 2
                    true
                }
                else -> false
            }
        }

        // Mantenemos las 3 pantallas cargadas para que el deslizamiento no tenga lag
        viewPager.offscreenPageLimit = 2
    }
}