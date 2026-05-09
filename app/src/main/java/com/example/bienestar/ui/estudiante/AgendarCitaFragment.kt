package com.example.bienestar.ui.estudiante

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bienestar.R
import com.example.bienestar.model.Horario
import com.example.bienestar.model.Profesional
import com.example.bienestar.network.RetrofitClient // Usamos RetrofitClient para consistencia
import com.example.bienestar.utils.SessionManager // 🎯 Importación necesaria
import com.example.bienestar.viewmodel.EstudianteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class AgendarCitaFragment : Fragment(R.layout.fragment_agendar_cita) {

    private var fechaSeleccionada = ""
    private var idProfesionalSeleccionado: Long = -1L
    private var idHorarioSeleccionado: Long = -1L

    private var listaProfesionalesMaster = mutableListOf<Profesional>()
    private var listaProfesionalesFiltrados = mutableListOf<Profesional>()
    private var listaHorarios = mutableListOf<Horario>()

    private lateinit var viewModel: EstudianteViewModel
    private lateinit var sessionManager: SessionManager // 🎯 Declaración del SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🎯 Inicialización
        sessionManager = SessionManager(requireContext())
        viewModel = ViewModelProvider(requireActivity()).get(EstudianteViewModel::class.java)

        val spinnerEspecialidad = view.findViewById<Spinner>(R.id.spinnerEspecialidad)
        val spinnerProfesionales = view.findViewById<Spinner>(R.id.spinnerProfesionales)
        val spinnerHorarios = view.findViewById<Spinner>(R.id.spinnerHorarios)
        val tvFecha = view.findViewById<TextView>(R.id.tvFechaSeleccionada)
        val btnConfirmar = view.findViewById<Button>(R.id.btnConfirmarCita)
        val etMotivo = view.findViewById<EditText>(R.id.etMotivoCita)

        etMotivo.setTextColor(android.graphics.Color.parseColor("#212121"))

        // --- 1. CONFIGURACIÓN DE ESPECIALIDADES ---
        val especialidades = arrayOf("Seleccione Especialidad", "Psicología", "Nutrición", "Fisioterapia", "Apoyo Académico")
        val adapterEsp = ArrayAdapter(requireContext(), R.layout.item_spinner_seleccionado, especialidades)
        adapterEsp.setDropDownViewResource(R.layout.item_spinner_texto)
        spinnerEspecialidad.adapter = adapterEsp

        spinnerProfesionales.isEnabled = false
        spinnerHorarios.isEnabled = false

        cargarProfesionalesMaster()

        // --- 2. FILTRO: ESPECIALIDAD -> PROFESIONAL ---
        spinnerEspecialidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val especialidadElegida = especialidades[position]
                if (position == 0) {
                    spinnerProfesionales.adapter = null
                    spinnerProfesionales.isEnabled = false
                } else {
                    listaProfesionalesFiltrados = listaProfesionalesMaster.filter {
                        it.especialidad.contains(especialidadElegida, ignoreCase = true)
                    }.toMutableList()

                    if (listaProfesionalesFiltrados.isEmpty()) {
                        spinnerProfesionales.adapter = null
                        spinnerProfesionales.isEnabled = false
                        Toast.makeText(requireContext(), "No hay profesionales disponibles", Toast.LENGTH_SHORT).show()
                    } else {
                        val nombres = listaProfesionalesFiltrados.map { it.nombre }
                        val adapterProf = ArrayAdapter(requireContext(), R.layout.item_spinner_seleccionado, nombres)
                        adapterProf.setDropDownViewResource(R.layout.item_spinner_texto)
                        spinnerProfesionales.adapter = adapterProf
                        spinnerProfesionales.isEnabled = true
                    }
                }
                spinnerHorarios.adapter = null
                spinnerHorarios.isEnabled = false
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        // --- 3. FILTRO: PROFESIONAL -> HORARIOS ---
        spinnerProfesionales.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (listaProfesionalesFiltrados.isNotEmpty() && position < listaProfesionalesFiltrados.size) {
                    val prof = listaProfesionalesFiltrados[position]
                    idProfesionalSeleccionado = prof.id
                    cargarHorariosReales(idProfesionalSeleccionado, spinnerHorarios)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        // --- 4. SELECTOR DE FECHA ---
        tvFecha.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, y, m, d ->
                val mes = String.format("%02d", m + 1)
                val dia = String.format("%02d", d)
                fechaSeleccionada = "$y-$mes-$dia"
                tvFecha.text = fechaSeleccionada
                tvFecha.setTextColor(android.graphics.Color.parseColor("#212121"))
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        // --- 5. CONFIRMACIÓN Y ENVÍO DINÁMICO ---
        btnConfirmar.setOnClickListener {
            val posHorario = spinnerHorarios.selectedItemPosition

            if (fechaSeleccionada.isEmpty() || idProfesionalSeleccionado == -1L || posHorario == -1) {
                Toast.makeText(requireContext(), "⚠️ Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnConfirmar.isEnabled = false
            btnConfirmar.text = "Agendando..."

            idHorarioSeleccionado = listaHorarios[posHorario].id ?: 0L

            // 🎯 OBTENER ID REAL DE LA SESIÓN
            val idEstudiante = sessionManager.obtenerIdUsuario()

            val body = hashMapOf<String, Any>(
                "profesionalId" to idProfesionalSeleccionado,
                "horarioId" to idHorarioSeleccionado,
                "fecha" to fechaSeleccionada,
                "motivo" to etMotivo.text.toString().ifEmpty { "Consulta de Bienestar" }
            )

            // 🎯 LLAMADA CORREGIDA AL VIEWMODEL (ID + BODY)
            viewModel.agendarCita(idEstudiante, body)
        }

        // --- 6. OBSERVADOR DEL RESULTADO ---
        viewModel.agendarResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Toast.makeText(requireContext(), "✅ Cita agendada con éxito", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
            result.onFailure { error ->
                btnConfirmar.isEnabled = true
                btnConfirmar.text = "CONFIRMAR CITA"
                Log.e("API_ERROR", "Fallo al agendar: ${error.message}")
                Toast.makeText(requireContext(), "❌ Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun cargarProfesionalesMaster() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getTodosLosProfesionales()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        listaProfesionalesMaster = response.body()?.toMutableList() ?: mutableListOf()
                    }
                }
            } catch (e: Exception) {
                Log.e("API", "Error: ${e.message}")
            }
        }
    }

    private fun cargarHorariosReales(profId: Long, spinner: Spinner) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getHorariosPorProfesional(profId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        listaHorarios = response.body()!!.toMutableList()
                        if (listaHorarios.isNotEmpty()) {
                            val labels = listaHorarios.map { "${it.dia}: ${it.horaInicio}" }
                            val adapterHor = ArrayAdapter(requireContext(), R.layout.item_spinner_seleccionado, labels)
                            adapterHor.setDropDownViewResource(R.layout.item_spinner_texto)
                            spinner.adapter = adapterHor
                            spinner.isEnabled = true
                        } else {
                            spinner.adapter = null
                            spinner.isEnabled = false
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("API", "Error: ${e.message}")
            }
        }
    }
}