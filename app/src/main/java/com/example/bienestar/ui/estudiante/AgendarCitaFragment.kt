package com.example.bienestar.ui.estudiante

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bienestar.R
import com.example.bienestar.model.Horario
import com.example.bienestar.viewmodel.EstudianteViewModel
import java.util.Calendar

class AgendarCitaFragment : Fragment(R.layout.fragment_agendar_cita) {

    private var fechaSeleccionada = ""
    private var idProfesionalSeleccionado: Long = 1L
    private var listaHorarios = mutableListOf<Horario>()

    // 1. Declaramos el ViewModel
    private lateinit var viewModel: EstudianteViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. IMPORTANTE: Inicializamos el ViewModel con 'requireActivity()'
        viewModel = ViewModelProvider(requireActivity()).get(EstudianteViewModel::class.java)

        val spinnerEspecialidad = view.findViewById<Spinner>(R.id.spinnerEspecialidad)
        val spinnerProfesionales = view.findViewById<Spinner>(R.id.spinnerProfesionales)
        val spinnerHorarios = view.findViewById<Spinner>(R.id.spinnerHorarios)
        val tvFecha = view.findViewById<TextView>(R.id.tvFechaSeleccionada)
        val btnConfirmar = view.findViewById<Button>(R.id.btnConfirmarCita)
        val etMotivo = view.findViewById<EditText>(R.id.etMotivoCita)

        etMotivo.setTextColor(android.graphics.Color.parseColor("#212121"))

        // --- Configuración de Spinners (Se mantiene igual) ---
        val especialidades = arrayOf("Todos", "Psicología", "Apoyo Académico")
        val adapterEsp = ArrayAdapter(requireContext(), R.layout.item_spinner_texto, especialidades)
        adapterEsp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEspecialidad.adapter = adapterEsp

        val todosProfesionales = listOf("Dr. Pérez (Psicología)", "Dra. Silva (Psicología)", "Dra. Gómez (Apoyo Académico)")

        spinnerEspecialidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val filtro = especialidades[position]
                val filtrados = if (filtro == "Todos") todosProfesionales else todosProfesionales.filter { it.contains(filtro) }
                val adapterProf = ArrayAdapter(requireContext(), R.layout.item_spinner_texto, filtrados)
                adapterProf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerProfesionales.adapter = adapterProf
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerProfesionales.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                idProfesionalSeleccionado = 1L
                cargarHorarios(idProfesionalSeleccionado, spinnerHorarios)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        // 3. SELECTOR DE FECHA
        tvFecha.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, y, m, d ->
                val mesFormateado = String.format("%02d", m + 1)
                val diaFormateado = String.format("%02d", d)
                fechaSeleccionada = "$y-$mesFormateado-$diaFormateado"
                tvFecha.text = fechaSeleccionada
                tvFecha.setTextColor(android.graphics.Color.parseColor("#212121"))
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        // 4. OBSERVAR RESULTADO DEL VIEWMODEL
        // Esto es lo que cierra la pantalla cuando todo sale bien
        viewModel.agendarResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Toast.makeText(requireContext(), "✅ ¡CITA AGENDADA CON ÉXITO!", Toast.LENGTH_LONG).show()
                parentFragmentManager.popBackStack() // Volver atrás
            }
            result.onFailure { error ->
                Log.e("API_ERROR", "Fallo: ${error.message}")
                Toast.makeText(requireContext(), "❌ Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // 5. BOTÓN CONFIRMAR (Ahora usa el ViewModel)
        btnConfirmar.setOnClickListener {
            val horarioPosicion = spinnerHorarios.selectedItemPosition
            if (fechaSeleccionada.isEmpty() || horarioPosicion == AdapterView.INVALID_POSITION) {
                Toast.makeText(requireContext(), "⚠️ Selecciona fecha y horario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val horarioSeleccionado = listaHorarios[horarioPosicion]

            val bodyCita = hashMapOf<String, Any>(
                "profesionalId" to 1L,
                "horarioId" to horarioSeleccionado.id,
                "fecha" to fechaSeleccionada,
                "motivo" to etMotivo.text.toString().ifEmpty { "Consulta Bienestar" }
            )

            // USAMOS EL VIEWMODEL (Él se encarga del launch y de recargar los datos)
            viewModel.agendarCita(bodyCita)
        }
    }

    private fun cargarHorarios(profesionalId: Long, spinner: Spinner) {
        listaHorarios = mutableListOf(
            Horario(id = 1L, profesionalId = 1L, dia = "Lunes", horaInicio = "08:00:00", horaFin = "12:00:00", activo = true),
            Horario(id = 2L, profesionalId = 1L, dia = "Miércoles", horaInicio = "14:00:00", horaFin = "18:00:00", activo = true),
            Horario(id = 3L, profesionalId = 1L, dia = "Viernes", horaInicio = "09:00:00", horaFin = "13:00:00", activo = true)
        )
        val labels = listaHorarios.map { "${it.dia}: ${it.horaInicio} - ${it.horaFin}" }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner_texto, labels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}