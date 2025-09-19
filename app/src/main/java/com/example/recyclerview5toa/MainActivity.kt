package com.example.recyclerview5toa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerPersonas)

        // This creates a vertical Layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class Alumno
        val data = ArrayList<Alumno>()

        // Add elements to my data list
        for (i in 1..50) {
            data.add(Alumno("Carlos Alexis Lopez", "20206826", "clopez89@ucol.mx", "https://i.pinimg.com/236x/e0/b8/3e/e0b83e84afe193922892917ddea28109.jpg"))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = AlumnoAdapter(this, data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }
}