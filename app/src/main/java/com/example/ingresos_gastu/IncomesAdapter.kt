package com.example.ingresos_gastu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class IncomesAdapter(private var incomes: List<Income>, private val context: Context) : RecyclerView.Adapter<IncomesAdapter.IncomeViewHolder>() {

    private val db: IncomesDatabaseHelper = IncomesDatabaseHelper(context)

    class IncomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val conceptTextView: TextView = itemView.findViewById(R.id.conceptTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)

        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)

        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IncomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.income_item, parent, false)
        return IncomeViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: IncomeViewHolder,
        position: Int
    ) {
        val income = incomes[position]
        holder.conceptTextView.text = income.concept
        holder.descriptionTextView.text = income.description
        holder.amountTextView.text = String.format(Locale.getDefault(), "$ %.2f", income.amount)

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateIncomeActivity::class.java).apply {
                putExtra("income_id", income.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteIncome(income.id)
            refreshData(db.getAllIncomes())
            Toast.makeText(holder.itemView.context, "Ingreso eliminado", Toast.LENGTH_SHORT)
        }
    }

    override fun getItemCount(): Int = incomes.size

    fun refreshData(newIncomes: List<Income>){
        incomes = newIncomes
        notifyDataSetChanged()
    }
}