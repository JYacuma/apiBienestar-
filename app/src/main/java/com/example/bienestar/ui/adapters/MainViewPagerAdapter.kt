package com.example.bienestar.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.example.bienestar.ui.estudiante.InicioEstudianteFragment
import com.example.bienestar.ui.estudiante.MisCitasFragment
import com.example.bienestar.ui.estudiante.PedirApoyoFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    // Volvemos a 3 opciones principales
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InicioEstudianteFragment()
            1 -> MisCitasFragment()     // Aquí vemos las agendadas y está el botón (+)
            2 -> PedirApoyoFragment()   // Pantalla exclusiva para solicitar apoyo
            else -> InicioEstudianteFragment()
        }
    }
}