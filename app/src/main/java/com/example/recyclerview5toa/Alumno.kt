package com.example.recyclerview5toa

// Se elimina la dependencia de Room. La clase de datos es simple.
data class Alumno (
    // Se elimina el autogenerate de Room, pero mantenemos el 'id' como marcador.
    // Usaremos la POSICIÓN en el ArrayList como ID temporal para la edición.
    val id: Int = 0, // Se mantiene 'id' para consistencia, aunque no se usa como PK aquí
    val nombre: String,
    val cuenta: String,
    val correo: String,
    val imagen: String
)