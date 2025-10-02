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

class ProyeccionesAdapter(
    private var proyecciones: List<Proyeccion>,
    private val context: Context
) : RecyclerView.Adapter<ProyeccionesAdapter.ProyeccionViewHolder>() {

    private val db: ProyeccionesDatabaseHelper = ProyeccionesDatabaseHelper(context)

    class ProyeccionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val conceptTextView: TextView = itemView.findViewById(R.id.conceptTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
        val fechaIngresoTextView: TextView = itemView.findViewById(R.id.fechaIngresoTextView)
        val fechaRecordatorioTextView: TextView = itemView.findViewById(R.id.fechaRecordatorioTextView)
        val estadoTextView: TextView = itemView.findViewById(R.id.estadoTextView)

        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProyeccionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.proyeccion_item, parent, false)
        return ProyeccionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProyeccionViewHolder, position: Int) {
        val proyeccion = proyecciones[position]
        holder.conceptTextView.text = proyeccion.concepto
        holder.descriptionTextView.text = proyeccion.descripcion
        holder.amountTextView.text = String.format(Locale.getDefault(), "$ %.2f", proyeccion.monto)
        holder.fechaIngresoTextView.text = "Ingreso: ${proyeccion.fechaIngreso}"
        holder.fechaRecordatorioTextView.text = "Recordatorio: ${proyeccion.fechaRecordatorio}"
        holder.estadoTextView.text = "Estado: ${proyeccion.estado}"

        // Botón editar
        holder.updateButton.setOnClickListener {
            val intent = Intent(context, Activity_update_proyeccion::class.java)
            intent.putExtra("proyeccion_id", proyeccion.id)
            context.startActivity(intent)
        }

        // Botón eliminar
        holder.deleteButton.setOnClickListener {
            db.deleteProyeccion(proyeccion.id)
            refreshData(db.getAllProyecciones())
            Toast.makeText(holder.itemView.context, "Proyección eliminada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = proyecciones.size

    fun refreshData(newProyecciones: List<Proyeccion>) {
        proyecciones = newProyecciones
        notifyDataSetChanged()
    }
}
