package com.example.recyclerview5toa

import android.app.Activity // Importar para usar RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview5toa.databinding.ActivityMainNuevoBinding

class MainActivityNuevo : AppCompatActivity() {

    private lateinit var binding: ActivityMainNuevoBinding
    private var idAlumno: Int? = null
    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainNuevoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val parExtra = intent.extras
        if (parExtra?.getString("mensaje") == "edit") {
            isEditMode = true
            idAlumno = parExtra.getInt("idA")

            binding.txtDato.text = "Editar Alumno"
            binding.txtNombre.setText(parExtra.getString("nombre"))
            binding.txtCuenta.setText(parExtra.getString("cuenta"))
            binding.txtCorreo.setText(parExtra.getString("correo"))
            binding.txtImage.setText(parExtra.getString("image"))
        } else {
            binding.txtDato.text = "Nuevo Alumno"
        }

        binding.btnGuardar.setOnClickListener {

            val nombre = binding.txtNombre.text.toString()
            val cuenta = binding.txtCuenta.text.toString()
            val correo = binding.txtCorreo.text.toString()
            val image = binding.txtImage.text.toString()

            // 1. Crear el Intent para devolver el resultado
            val intento2 = Intent()

            if (isEditMode && idAlumno != null) {
                intento2.putExtra("mensaje", "edit")
                intento2.putExtra("idA", idAlumno!!)
            } else {
                intento2.putExtra("mensaje", "nuevo")
            }

            // 2. Mandamos los valores
            intento2.putExtra("nombre", nombre)
            intento2.putExtra("cuenta", cuenta)
            intento2.putExtra("correo", correo)
            intento2.putExtra("image", image)

            // 3. ⭐ CORRECCIÓN CLAVE: Enviamos el resultado y cerramos la actividad ⭐
            setResult(Activity.RESULT_OK, intento2)
            finish()
        }
    }
}