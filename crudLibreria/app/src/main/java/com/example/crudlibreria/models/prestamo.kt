package com.example.crudlibreria.models

import java.util.Date

data class prestamo (
    var id:String,
    var fecha_prestamo:String,
    var fecha_devolucion: String,
    var Estado: Int,
    var usuario_prestamo: usuario,
    var libro_prestamo: libro

)