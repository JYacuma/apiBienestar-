package com.example.bienestar.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bienestar.ui.profesional.InicioProfesionalFragment
import com.example.bienestar.ui.profesional.AgendaProfesionalFragment
import com.example.bienestar.ui.profesional.MisHorariosFragment

class ProfesionalViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    // 🎯 Ahora manejamos 3 secciones
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InicioProfesionalFragment()
            1 -> AgendaProfesionalFragment()
            2 -> MisHorariosFragment() // 🎯 La nueva vista de gestión
            else -> InicioProfesionalFragment()
        }
    }
}