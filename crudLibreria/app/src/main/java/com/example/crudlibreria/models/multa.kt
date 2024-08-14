package com.example.crudlibreria.models

import java.util.Date

class multa (
    var id:String,
    var fecha_multa: Date,
    var usuario_multado: Int,
    var prestamo: Int,
    var valor_multa: Int,
    var estado_multa: Int
)

