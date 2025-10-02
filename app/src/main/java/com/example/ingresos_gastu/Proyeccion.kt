package com.example.ingresos_gastu

data class Proyeccion(
    val id: Int,
    val concepto: String,
    val descripcion: String,
    val monto: Double,
    val fechaIngreso: String,
    val fechaRecordatorio: String,
    val estado: String
)
