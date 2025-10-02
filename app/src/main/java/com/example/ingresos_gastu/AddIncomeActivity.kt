package com.example.ingresos_gastu

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ingresos_gastu.databinding.ActivityAddIncomeBinding

class AddIncomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddIncomeBinding
    private lateinit var db: IncomesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = IncomesDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val selectedId = binding.conceptRadioGroup.checkedRadioButtonId
            val concept = if (selectedId != -1) {
                val radioButton = findViewById<RadioButton>(selectedId)
                radioButton.text.toString()
            } else {
                ""
            }

            val description = binding.descriptionEditText.text.toString()
            val amount = binding.amountEditText.text.toString().toDoubleOrNull() ?: 0.0

            val income = Income(0, concept, description, amount)
            db.insertIncome(income)

            Toast.makeText(this, "Ingreso guardado", Toast.LENGTH_SHORT).show()
            finish()
        }

        val conceptToggle = findViewById<TextView>(R.id.conceptToggle)
        val conceptContainer = findViewById<LinearLayout>(R.id.conceptContainer)
        val conceptRadioGroup = findViewById<RadioGroup>(R.id.conceptRadioGroup)

        // Toggle mostrar/ocultar acordeón
        conceptToggle.setOnClickListener {
            if (conceptContainer.visibility == View.GONE) {
                conceptContainer.visibility = View.VISIBLE
                conceptToggle.text = "Concepto ▲"
            } else {
                conceptContainer.visibility = View.GONE
                conceptToggle.text = "Concepto ▼"
            }
        }

        // Escuchar selección
        conceptRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selected = findViewById<RadioButton>(checkedId)
            Toast.makeText(this, "Seleccionaste: ${selected.text}", Toast.LENGTH_SHORT).show()
        }
    }
}
