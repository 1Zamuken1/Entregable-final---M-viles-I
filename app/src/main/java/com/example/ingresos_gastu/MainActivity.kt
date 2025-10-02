package com.example.ingresos_gastu

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ingresos_gastu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: IncomesDatabaseHelper
    private lateinit var incomesAdapter: IncomesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = IncomesDatabaseHelper(this)
        incomesAdapter = IncomesAdapter(db.getAllIncomes(), this)

        binding.incomesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.incomesRecyclerView.adapter = incomesAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddIncomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        incomesAdapter.refreshData(db.getAllIncomes())
    }
}