# IngresosGastu
Aplicación móvil en **Kotlin** para el registro y control de **ingresos** y **proyecciones**, como parte del módulo de Ingresos de Gastu.

---
## Descripción
Este proyecto educativo es una aplicación desarrollada en **Kotlin** con **Android Studio**, que permite gestionar de manera sencilla los ingresos personales y proyecciones financieras.  

El objetivo es practicar la creación de **CRUDs** con `SQLite`, el uso de **Activities**, **ViewBinding**, y navegación entre pantallas.

---
## ¿Cómo se usa?
1. Al iniciar la aplicación se muestra un **selector principal** con dos botones:
   - **Ingresos** → Permite gestionar los ingresos personales.
   - **Proyecciones** → Permite gestionar proyecciones financieras futuras.
   
2. Cada botón muestra dentro de sí el **total acumulado** correspondiente.

3. Al entrar en cada sección, el usuario puede:
   - **Ingresos** → Crear, editar y eliminar registros con los campos:
     - Concepto  
     - Descripción  
     - Monto  

   - **Proyecciones** → Crear, editar y eliminar registros con los campos:
     - Concepto  
     - Descripción  
     - Monto  
     - Fecha de ingreso  
     - Fecha de recordatorio  
     - Estado  

4. Todos los datos se guardan de forma local en una **base de datos SQLite**, por lo que permanecen disponibles incluso al cerrar la aplicación.

---
## Estructura del proyecto
- `app/src/main/java/com/example/ingresos_gastu/`
  - `IncomesDatabaseHelper.kt` → Helper para CRUD de ingresos
  - `ProyeccionesDatabaseHelper.kt` → Helper para CRUD de proyecciones
  - `Income.kt` → Data class para ingresos
  - `Proyeccion.kt` → Data class para proyecciones
  - `AddIncomeActivity.kt` → Pantalla para agregar ingresos
  - `AddProyeccionActivity.kt` → Pantalla para agregar proyecciones
  - `MainActivity.kt` → Vista principal de ingresos
  - `ProyeccionesActivity.kt` → Vista principal de proyecciones
  - `SelectorActivity.kt` → Pantalla inicial con los botones y totales
- `res/layout/` → Archivos XML de interfaces gráficas
- `res/values/` → Colores, estilos y strings

---
## Requisitos previos
- [Kotlin](https://kotlinlang.org/) 1.9 o superior
- [Android Studio](https://developer.android.com/studio) (versión recomendada: Giraffe o superior)
- Emulador Android o dispositivo físico con Android 8.0+

---
# Créditos
**Proyecto desarrollado por:** [`Juan Esteban Barrios P.`](https://github.com/1Zamuken1)  
**Instructor(a):** [`Mónica Mendoza C.`]  
**Institución:** [`SENA - Centro de Servicios Financieros`]  

---
