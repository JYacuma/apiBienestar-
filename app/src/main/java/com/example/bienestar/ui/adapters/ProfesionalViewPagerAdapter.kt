package com.example.bienestar.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bienestar.ui.profesional.InicioProfesionalFragment
import com.example.bienestar.ui.profesional.AgendaProfesionalFragment

class ProfesionalViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2 // Solo tiene Inicio y Agenda

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InicioProfesionalFragment()
            1 -> AgendaProfesionalFragment()
            else -> InicioProfesionalFragment()
        }
    }
}