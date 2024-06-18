package com.example.crudlibreria.models

data class libro(
    var id:String,
    var titulo: String,
    var autor: String,
    var isbn: String,
    var genero: String,
    var num_ejem_disponible: Int,
    var num_ejem_ocupados: Int
)
