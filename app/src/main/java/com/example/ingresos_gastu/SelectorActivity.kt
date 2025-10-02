package com.example.ingresos_gastu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class SelectorActivity : AppCompatActivity() {

    private lateinit var btnIngresos: MaterialButton
    private lateinit var btnProyecciones: MaterialButton

    private lateinit var incomesDb: IncomesDatabaseHelper
    private lateinit var proyeccionesDb: ProyeccionesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selector_activity)

        // Inicializar botones
        btnIngresos = findViewById(R.id.btnIngresos)
        btnProyecciones = findViewById(R.id.btnProyecciones)

        // Inicializar bases de datos
        incomesDb = IncomesDatabaseHelper(this)
        proyeccionesDb = ProyeccionesDatabaseHelper(this)

        // Navegación
        btnIngresos.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnProyecciones.setOnClickListener {
            val intent = Intent(this, ProyeccionesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Calcular totales cada vez que la actividad vuelve a primer plano
        val totalIngresos = incomesDb.getAllIncomes().sumOf { it.amount }
        val totalProyecciones = proyeccionesDb.getAllProyecciones().sumOf { it.monto }

        // Mostrar totales dentro del mismo botón
        btnIngresos.text = "Ingresos\nTotal: $%.2f".format(totalIngresos)
        btnProyecciones.text = "Proyecciones\nTotal: $%.2f".format(totalProyecciones)
    }
}
