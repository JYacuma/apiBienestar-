package com.example.bienestar.ui.estudiante

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bienestar.R
import com.example.bienestar.ui.adapters.CitaAdapter
import com.example.bienestar.viewmodel.EstudianteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MisCitasFragment : Fragment(R.layout.fragment_mis_citas) {

    private lateinit var viewModel: EstudianteViewModel
    private lateinit var adapter: CitaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // IMPORTANTE: Usamos 'requireActivity()' para compartir el ViewModel con el Inicio
        viewModel = ViewModelProvider(requireActivity()).get(EstudianteViewModel::class.java)

        val rvMisCitas = view.findViewById<RecyclerView>(R.id.rvMisCitas)
        rvMisCitas.layoutManager = LinearLayoutManager(requireContext())

        // Inicializamos con lista vacía
        adapter = CitaAdapter(emptyList())
        rvMisCitas.adapter = adapter

        // OBSERVAR: Cuando las citas cambien en el ViewModel, el RecyclerView se actualiza solo
        viewModel.citas.observe(viewLifecycleOwner) { listaCitas ->
            adapter.updateList(listaCitas)
        }

        // Cargar datos (ID 1 fijo como acordamos)
        viewModel.cargarDatos(1L)

        val fabAgendar = view.findViewById<FloatingActionButton>(R.id.fabAgendarCita)
        fabAgendar.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.detail_container_estudiante, AgendarCitaFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}