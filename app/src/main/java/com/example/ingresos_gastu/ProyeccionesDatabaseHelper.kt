package com.example.ingresos_gastu

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProyeccionesDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "proyecciones-gastu.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "proyecciones"

        private const val COLUMN_ID = "id"
        private const val COLUMN_CONCEPT = "concepto"
        private const val COLUMN_DESCRIPTION = "descripcion"
        private const val COLUMN_AMOUNT = "monto"

        private const val COLUMN_FECHA_INGRESO = "fechaIngreso"

        private const val COLUMN_FECHA_RECORDATORIO = "fechaRecordatorio"

        private const val COLUMN_ESTADO = "estado"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
    CREATE TABLE $TABLE_NAME (
        $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
        $COLUMN_CONCEPT TEXT, 
        $COLUMN_DESCRIPTION TEXT,  
        $COLUMN_AMOUNT DOUBLE,
        $COLUMN_FECHA_INGRESO TEXT,
        $COLUMN_FECHA_RECORDATORIO TEXT,
        $COLUMN_ESTADO TEXT
    )
""".trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    // Insertar proyección
    fun insertProyeccion(proyeccion: Proyeccion) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CONCEPT, proyeccion.concepto)
            put(COLUMN_DESCRIPTION, proyeccion.descripcion)
            put(COLUMN_AMOUNT, proyeccion.monto)
            put(COLUMN_FECHA_INGRESO, proyeccion.fechaIngreso)
            put(COLUMN_FECHA_RECORDATORIO, proyeccion.fechaRecordatorio)
            put(COLUMN_ESTADO, proyeccion.estado)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Obtener todas
    fun getAllProyecciones(): List<Proyeccion> {
        val lista = mutableListOf<Proyeccion>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val concepto = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONCEPT))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            val monto = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
            val fechaIngreso = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_INGRESO))
            val fechaRecordatorio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_RECORDATORIO))
            val estado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTADO))

            val proyeccion = Proyeccion(
                id,
                concepto,
                descripcion,
                monto,
                fechaIngreso,
                fechaRecordatorio,
                estado
            )
            lista.add(proyeccion)
        }

        cursor.close()
        db.close()
        return lista
    }

    // Obtener por ID
    fun getProyeccionByID(proyeccionId: Int): Proyeccion {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $proyeccionId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val concepto = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONCEPT))
        val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
        val monto = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
        val fechaIngreso = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_INGRESO))
        val fechaRecordatorio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_RECORDATORIO))
        val estado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTADO))

        cursor.close()
        db.close()
        return Proyeccion(
            id,
            concepto,
            descripcion,
            monto,
            fechaIngreso,
            fechaRecordatorio,
            estado
        )
    }

    // Actualizar
    fun updateProyeccion(proyeccion: Proyeccion) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CONCEPT, proyeccion.concepto)
            put(COLUMN_DESCRIPTION, proyeccion.descripcion)
            put(COLUMN_AMOUNT, proyeccion.monto)
            put(COLUMN_FECHA_INGRESO, proyeccion.fechaIngreso)
            put(COLUMN_FECHA_RECORDATORIO, proyeccion.fechaRecordatorio)
            put(COLUMN_ESTADO, proyeccion.estado)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(proyeccion.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    // Eliminar
    fun deleteProyeccion(proyeccionId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(proyeccionId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}
