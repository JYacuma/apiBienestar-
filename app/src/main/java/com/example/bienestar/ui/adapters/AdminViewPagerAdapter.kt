package com.example.bienestar.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bienestar.ui.admin.InicioAdminFragment
import com.example.bienestar.ui.admin.UsuariosAdminFragment
import com.example.bienestar.ui.admin.HorariosAdminFragment

class AdminViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3 // Inicio, Usuarios, Horarios

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InicioAdminFragment()
            1 -> UsuariosAdminFragment()
            2 -> HorariosAdminFragment()
            else -> InicioAdminFragment()
        }
    }
}