package com.example.crudlibreria.models

import android.widget.Spinner

data class usuario (
    var id:String,
    var nombre: String,
    var direccion: String,
    var correo: String,
    var tipoUsuario: Int
)