package com.example.ingresos_gastu

import android.R
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class IncomesDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Datos para la bd
    companion object{
        private const val DATABASE_NAME = "ingresos-gastu.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "ingresos"
        private const val COLUMN_ID = "id"

        private const val COLUMN_CONCEPT = "concept"

        private const val COLUMN_DESCRIPTION = "description"

        private const val COLUMN_AMOUNT = "amount"
    }

    // Se implementa el SQLiteOpenHelper con las variables del companion object
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_CONCEPT TEXT, $COLUMN_DESCRIPTION TEXT,  $COLUMN_AMOUNT DOUBLE)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    // Función de insertar

    fun insertIncome(income: Income){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CONCEPT,income.concept)
            put(COLUMN_DESCRIPTION,income.description)
            put(COLUMN_AMOUNT,income.amount)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Función Obtener todos

    fun getAllIncomes(): List<Income> {
        val incomesList = mutableListOf<Income>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val concept = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONCEPT))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))

            val income = Income(id, concept, description, amount)
            incomesList.add(income)
        }
        cursor.close()
        db.close()
        return incomesList
    }

    // Función editar

    fun updateIncome(income: Income){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CONCEPT, income.concept)
            put(COLUMN_DESCRIPTION, income.description)
            put(COLUMN_AMOUNT, income.amount)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(income.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getIncomeByID(incomeId: Int): Income{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $incomeId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val concept = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONCEPT))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
        val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))

        cursor.close()
        db.close()
        return Income(id, concept, description, amount)
    }

    // Función eliminar

    fun deleteIncome(incomeId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(incomeId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }


}