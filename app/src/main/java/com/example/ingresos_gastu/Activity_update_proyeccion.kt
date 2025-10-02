package com.example.ingresos_gastu

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ingresos_gastu.databinding.ActivityUpdateProyeccionBinding
import android.widget.ArrayAdapter
import java.util.Calendar

class Activity_update_proyeccion : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateProyeccionBinding
    private lateinit var db: ProyeccionesDatabaseHelper
    private var proyeccionId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProyeccionBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        db = ProyeccionesDatabaseHelper(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recibir ID de la proyección
        proyeccionId = intent.getIntExtra("proyeccion_id", -1)
        if (proyeccionId == -1) {
            finish()
            return
        }


        // Obtener la proyección de la DB
        val proyeccion = db.getProyeccionByID(proyeccionId)
        if (proyeccion == null) {
            Toast.makeText(this, "La proyección no existe", Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        // Setear valores en los campos
        binding.conceptToggleProyeccionUpdate.text = proyeccion.concepto
        binding.descriptionProyeccionEditTextUpdate.setText(proyeccion.descripcion)
        binding.amountProyeccionEditTextUpdate.setText(proyeccion.monto.toString())
        binding.fechaIngresoEditTextUpdate.setText(proyeccion.fechaIngreso)
        binding.fechaRecordatorioEditTextUpdate.setText(proyeccion.fechaRecordatorio)

        // Spinner de estado
        ArrayAdapter.createFromResource(
            this,
            R.array.estado_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.estadoSpinnerUpdate.adapter = adapter
            val estadoIndex = if (proyeccion.estado == "Activo") 0 else 1
            binding.estadoSpinnerUpdate.setSelection(estadoIndex)
        }

        // Seleccionar RadioButton del concepto
        val selectedConcept = proyeccion.concepto
        for (i in 0 until binding.conceptRadioGroupProyeccionUpdate.childCount) {
            val child = binding.conceptRadioGroupProyeccionUpdate.getChildAt(i)
            if (child is RadioButton && child.text.toString().trim() == selectedConcept.trim()) {
                child.isChecked = true
                break
            }
        }

        // Listener para toggle del concepto
        binding.conceptToggleProyeccionUpdate.setOnClickListener {
            if (binding.conceptContainerProyeccionUpdate.visibility == android.view.View.GONE) {
                binding.conceptContainerProyeccionUpdate.visibility = android.view.View.VISIBLE
                binding.conceptToggleProyeccionUpdate.text = "Concepto ▲"
            } else {
                binding.conceptContainerProyeccionUpdate.visibility = android.view.View.GONE
                binding.conceptToggleProyeccionUpdate.text = "Concepto ▼"
            }
        }

        binding.conceptRadioGroupProyeccionUpdate.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            binding.conceptToggleProyeccionUpdate.text = radioButton?.text
        }

        // DatePickers para fechas
        binding.fechaIngresoEditTextUpdate.setOnClickListener { showDatePicker(binding.fechaIngresoEditTextUpdate) }
        binding.fechaRecordatorioEditTextUpdate.setOnClickListener { showDatePicker(binding.fechaRecordatorioEditTextUpdate) }

        // Botón Guardar
        binding.saveProyeccionButtonUpdate.setOnClickListener {
            guardarCambios()
        }
    }

    private fun showDatePicker(editText: android.widget.EditText) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, y, m, d ->
            val fecha = String.format("%02d/%02d/%04d", d, m + 1, y)
            editText.setText(fecha)
        }, year, month, day)
        dpd.show()
    }

    private fun guardarCambios() {
        val selectedId = binding.conceptRadioGroupProyeccionUpdate.checkedRadioButtonId
        val concepto = if (selectedId != -1) {
            binding.conceptRadioGroupProyeccionUpdate.findViewById<RadioButton>(selectedId).text.toString()
        } else {
            binding.conceptToggleProyeccionUpdate.text.toString()
        }

        val descripcion = binding.descriptionProyeccionEditTextUpdate.text.toString()
        val monto = binding.amountProyeccionEditTextUpdate.text.toString().toDoubleOrNull() ?: 0.0
        val fechaIngreso = binding.fechaIngresoEditTextUpdate.text.toString()
        val fechaRecordatorio = binding.fechaRecordatorioEditTextUpdate.text.toString()
        val estado = binding.estadoSpinnerUpdate.selectedItem.toString()

        val proyeccionEdit = Proyeccion(
            id = proyeccionId,
            concepto = concepto,
            descripcion = descripcion,
            monto = monto,
            fechaIngreso = fechaIngreso,
            fechaRecordatorio = fechaRecordatorio,
            estado = estado
        )

        db.updateProyeccion(proyeccionEdit)
        Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()
        finish()
    }
}
