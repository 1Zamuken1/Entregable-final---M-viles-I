package com.example.ingresos_gastu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ingresos_gastu.databinding.ProyeccionesActivityBinding

class ProyeccionesActivity : AppCompatActivity() {

    private lateinit var binding: ProyeccionesActivityBinding
    private lateinit var db: ProyeccionesDatabaseHelper
    private lateinit var proyeccionesAdapter: ProyeccionesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProyeccionesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ProyeccionesDatabaseHelper(this)
        proyeccionesAdapter = ProyeccionesAdapter(db.getAllProyecciones(), this)

        binding.projectionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.projectionsRecyclerView.adapter = proyeccionesAdapter

        binding.addProjectionButton.setOnClickListener {
            val intent = Intent(this, AddProyeccionActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        proyeccionesAdapter.refreshData(db.getAllProyecciones())
    }
}
