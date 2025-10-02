package com.example.ingresos_gastu

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ingresos_gastu.databinding.ActivityAddProyeccionBinding
import java.util.*

class AddProyeccionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProyeccionBinding
    private lateinit var db: ProyeccionesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProyeccionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ProyeccionesDatabaseHelper(this)

        // Configurar Spinner de estado
        ArrayAdapter.createFromResource(
            this,
            R.array.estado_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.estadoSpinner.adapter = adapter
        }

        // Botón Guardar
        binding.saveProyeccionButton.setOnClickListener {
            guardarProyeccion()
        }

        // --- Acordeón Concepto ---
        binding.conceptToggleProyeccion.setOnClickListener {
            if (binding.conceptContainerProyeccion.visibility == View.GONE) {
                binding.conceptContainerProyeccion.visibility = View.VISIBLE
                binding.conceptToggleProyeccion.text = "Concepto ▲"
            } else {
                binding.conceptContainerProyeccion.visibility = View.GONE
                binding.conceptToggleProyeccion.text = "Concepto ▼"
            }
        }

        // Listener seguro del RadioGroup
        binding.conceptRadioGroupProyeccion.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                val selected = binding.conceptRadioGroupProyeccion.findViewById<RadioButton>(checkedId)
                Toast.makeText(this, "Seleccionaste: ${selected.text}", Toast.LENGTH_SHORT).show()
            }
        }

        // DatePickers para las fechas
        binding.fechaIngresoEditText.setOnClickListener { mostrarDatePicker(binding.fechaIngresoEditText) }
        binding.fechaRecordatorioEditText.setOnClickListener { mostrarDatePicker(binding.fechaRecordatorioEditText) }
    }

    private fun mostrarDatePicker(editText: android.widget.EditText) {
        val calendario = Calendar.getInstance()
        val year = calendario.get(Calendar.YEAR)
        val month = calendario.get(Calendar.MONTH)
        val day = calendario.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Ajustar mes (0-11)
            val mes = selectedMonth + 1
            val fechaFormateada = String.format("%02d/%02d/%04d", selectedDay, mes, selectedYear)
            editText.setText(fechaFormateada)
        }, year, month, day)

        datePicker.show()
    }

    private fun guardarProyeccion() {
        // Concepto seleccionado
        val selectedId = binding.conceptRadioGroupProyeccion.checkedRadioButtonId
        val concepto = if (selectedId != -1) {
            val radioButton = binding.conceptRadioGroupProyeccion.findViewById<RadioButton>(selectedId)
            radioButton.text.toString()
        } else {
            ""
        }

        // Otros campos
        val descripcion = binding.descriptionProyeccionEditText.text.toString()
        val monto = binding.amountProyeccionEditText.text.toString().toDoubleOrNull() ?: 0.0
        val fechaIngreso = binding.fechaIngresoEditText.text.toString()
        val fechaRecordatorio = binding.fechaRecordatorioEditText.text.toString()
        val estado = binding.estadoSpinner.selectedItem.toString()

        // Crear objeto Proyeccion
        val proyeccion = Proyeccion(
            id = 0,
            concepto = concepto,
            descripcion = descripcion,
            monto = monto,
            fechaIngreso = fechaIngreso,
            fechaRecordatorio = fechaRecordatorio,
            estado = estado
        )

        db.insertProyeccion(proyeccion)

        Toast.makeText(this, "Proyección guardada", Toast.LENGTH_SHORT).show()
        finish()
    }
}
