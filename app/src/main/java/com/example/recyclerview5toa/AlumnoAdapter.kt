package com.example.recyclerview5toa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
// CORRECCIÓN: Usar ItemPersonasBinding para item_personas.xml
import com.example.recyclerview5toa.databinding.ItemPersonasBinding

class AlumnoAdapter(
    private val context: Context,
    private val listAlumnos: List<Alumno>,
    private var optionsMenuClickListener: OptionsMenuClickListener
) : RecyclerView.Adapter<AlumnoAdapter.ViewHolder>() {

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int)
    }

    // CORRECCIÓN: Usar ItemPersonasBinding
    inner class ViewHolder(val binding: ItemPersonasBinding) : RecyclerView.ViewHolder(binding.root)

    // CORRECCIÓN: Usar ItemPersonasBinding para inflar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPersonasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(listAlumnos[position]) {
                // El layout usa imgAlumno
                Glide.with(context).load(this.imagen).into(binding.imgAlumno)

                binding.nombre.text = this.nombre
                binding.cuenta.text = this.cuenta

                binding.textViewOptions.setOnClickListener {
                    optionsMenuClickListener.onOptionsMenuClicked(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listAlumnos.size
    }
}