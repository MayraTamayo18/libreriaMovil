package com.example.crudlibreria.models

import java.util.Date

data class prestamo (
    var id:String,
    var fecha_prestamo:Date,
    var fecha_devolucion: Date,
    var Estado: Int,
    var usuario_prestamo: Int,
    var libro_prestamo: Int

)