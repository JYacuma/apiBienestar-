package com.example.bienestar.ui.estudiante

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bienestar.R
import com.example.bienestar.ui.adapters.MainViewPagerAdapter
import com.example.bienestar.utils.SessionManager // 🎯 Importación necesaria
import com.example.bienestar.viewmodel.EstudianteViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class EstudianteMainActivity : AppCompatActivity() {

    private val viewModel: EstudianteViewModel by viewModels()
    private lateinit var sessionManager: SessionManager // 🎯 Declaración

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante_main)

        // 🎯 Inicializar SessionManager
        sessionManager = SessionManager(this)

        // 🎯 CARGAR DATOS DEL ESTUDIANTE LOGUEADO AL ENTRAR
        val idUsuario = sessionManager.obtenerIdUsuario()
        if (idUsuario != -1L) {
            viewModel.cargarDatos(idUsuario)
        }

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val adapter = MainViewPagerAdapter(this)
        viewPager.adapter = adapter

        // 2. Sincronizar: ViewPager -> BottomNav
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
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

        viewPager.offscreenPageLimit = 2
    }
}