package com.example.ingresos_gastu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SelectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selector_activity)

        val btnIngresos = findViewById<Button>(R.id.btnIngresos)
        val btnProyecciones = findViewById<Button>(R.id.btnProyecciones)

        btnIngresos.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnProyecciones.setOnClickListener {
            val intent = Intent(this, ProyeccionesActivity::class.java)
            startActivity(intent)
        }
    }
}
