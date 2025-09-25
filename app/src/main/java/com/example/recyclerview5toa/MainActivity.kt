package com.example.recyclerview5toa

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview5toa.databinding.ActivityMainBinding
import androidx.core.view.get
import android.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var data = ArrayList<Alumno>()
    private lateinit var rvAdapter: AlumnoAdapter

    // Código de solicitud para identificar el resultado
    private val ADD_ALUMNO_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerPersonas.layoutManager = LinearLayoutManager(this)

        if (data.isEmpty()) {
            data.add(Alumno(1, "José Nabor", "20102345", "jmorfin@ucol.mx", "https://i.pinimg.com/236x/e0/b8/3e/e0b83e84afe193922892917ddea28109.jpg"))
            data.add(Alumno(2, "Luis Antonio", "20112345", "luis.a@ucol.mx", "https://i.pinimg.com/236x/e0/b8/3e/e0b83e84afe193922892917ddea28109.jpg"))
            data.add(Alumno(3, "Juan Pedro", "20122345", "juan.p@ucol.mx", "https://i.pinimg.com/236x/73/6x/9f/736x9f6fe02b4e7fe3f5080e22700346.jpg"))
        }

        rvAdapter = AlumnoAdapter(this, data, object : AlumnoAdapter.OptionsMenuClickListener {
            override fun onOptionsMenuClicked(position: Int) {
                itemOptionsMenu(position)
            }
        })

        binding.recyclerPersonas.adapter = rvAdapter

        // ⭐ CORRECCIÓN: Usar startActivityForResult para agregar nuevo alumno ⭐
        binding.faButton.setOnClickListener {
            val intento1 = Intent(this, MainActivityNuevo::class.java)
            startActivityForResult(intento1, ADD_ALUMNO_REQUEST)
        }

        // ⭐ Lógica de recepción de datos ELIMINADA del onCreate ⭐
        // La recepción ocurre ahora en onActivityResult
    }

    // ⭐ FUNCIÓN CRÍTICA PARA RECIBIR DATOS Y PERMITIR MÚLTIPLES AGREGADOS ⭐
    override fun onActivityResult(requestCode: Int, resultCode: Int, dataIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, dataIntent)

        // Asegurarse de que el código de solicitud sea para agregar alumnos y que el resultado sea OK
        if (requestCode == ADD_ALUMNO_REQUEST && resultCode == RESULT_OK) {
            val parExtra = dataIntent?.extras
            val msje = parExtra?.getString("mensaje")
            val nombre = parExtra?.getString("nombre")
            val cuenta = parExtra?.getString("cuenta")
            val correo = parExtra?.getString("correo")
            val image = parExtra?.getString("image")
            val idAlum = parExtra?.getInt("idA")

            // Lógica para agregar un nuevo alumno
            if (msje == "nuevo") {
                val insertIndex: Int = data.count()
                this.data.add(insertIndex,
                    Alumno(
                        id = 0,
                        nombre = nombre ?: "",
                        cuenta = cuenta ?: "",
                        correo = correo ?: "",
                        imagen = image ?: ""
                    )
                )
                rvAdapter.notifyItemInserted(insertIndex)
            }
            // Lógica para editar un alumno existente
            else if (msje == "edit" && idAlum != null) {
                if (idAlum >= 0 && idAlum < this.data.size) {
                    this.data[idAlum] = Alumno(
                        id = idAlum,
                        nombre = nombre ?: "",
                        cuenta = cuenta ?: "",
                        correo = correo ?: "",
                        imagen = image ?: ""
                    )
                    rvAdapter.notifyItemChanged(idAlum)
                }
            }
        }
    }

    private fun showDetailsDialog(alumno: Alumno) {
        val message = "Nombre: ${alumno.nombre}\n" +
                "Cuenta: ${alumno.cuenta}\n" +
                "Correo: ${alumno.correo}\n" +
                "Imagen URL: ${alumno.imagen}"

        AlertDialog.Builder(this)
            .setTitle("Detalles del Alumno")
            .setMessage(message)
            .setPositiveButton("Cerrar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun itemOptionsMenu(position: Int) {
        val popupMenu = PopupMenu(this, binding.recyclerPersonas[position].findViewById(R.id.textViewOptions))
        popupMenu.inflate(R.menu.options_menu)

        val intento2 = Intent(this, MainActivityNuevo::class.java)
        val alumnoSeleccionado = data[position]

        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId) {
                    R.id.borrar -> {
                        data.removeAt(position)
                        rvAdapter.notifyDataSetChanged()
                        return true
                    }
                    R.id.editar -> {
                        val idAlum: Int = position

                        // ⭐ CORRECCIÓN: Usar startActivityForResult para la edición también ⭐
                        intento2.putExtra("mensaje", "edit")
                        intento2.putExtra("idA", idAlum)
                        intento2.putExtra("nombre", alumnoSeleccionado.nombre)
                        intento2.putExtra("cuenta", alumnoSeleccionado.cuenta)
                        intento2.putExtra("correo", alumnoSeleccionado.correo)
                        intento2.putExtra("image", alumnoSeleccionado.imagen)
                        startActivityForResult(intento2, ADD_ALUMNO_REQUEST) // Usar el mismo código
                        return true
                    }
                    R.id.ver -> {
                        showDetailsDialog(alumnoSeleccionado)
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
    }
}