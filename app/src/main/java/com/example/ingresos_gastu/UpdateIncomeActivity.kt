package com.example.ingresos_gastu

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ingresos_gastu.databinding.ActivityUpdateBinding

class UpdateIncomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: IncomesDatabaseHelper

    private var incomeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        db = IncomesDatabaseHelper(this)

        // Recibir el ID del ingreso
        incomeId = intent.getIntExtra("income_id", -1)
        if (incomeId == -1) {
            finish()
            return
        }

        // Obtener el ingreso de la base de datos
        val income = db.getIncomeByID(incomeId)
        if (income == null) {
            Toast.makeText(this, "El ingreso no existe", Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        // Setear los valores existentes en los campos
        binding.updateConceptToggle.text = income.concept
        binding.updateDescriptionEditText.setText(income.description)
        binding.updateAmountEditText.setText(income.amount.toString())

        // Seleccionar el RadioButton que corresponda al concepto guardado
        val selectedConcept = income.concept
        for (i in 0 until binding.updateConceptRadioGroup.childCount) {
            val child = binding.updateConceptRadioGroup.getChildAt(i)
            if (child is RadioButton && child.text.toString().trim() == selectedConcept.trim()) {
                child.isChecked = true
                break
            }
        }


        // Listener para actualizar el toggle cuando cambie la selección
        binding.updateConceptRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            binding.updateConceptToggle.text = radioButton?.text
        }

        binding.updateSaveButton.setOnClickListener {
            // Obtener el RadioButton seleccionado
            val checkedId = binding.updateConceptRadioGroup.checkedRadioButtonId
            val selectedConcept = if (checkedId != -1) {
                binding.updateConceptRadioGroup.findViewById<RadioButton>(checkedId).text.toString()
            } else {
                binding.updateConceptToggle.text.toString() // fallback por si no seleccionó nada
            }

            val newDescription = binding.updateDescriptionEditText.text.toString()
            val newAmount = binding.updateAmountEditText.text.toString().toDouble()

            val editIncome = Income(incomeId, selectedConcept, newDescription, newAmount)
            db.updateIncome(editIncome)

            Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()
            finish()
        }


    }
}
