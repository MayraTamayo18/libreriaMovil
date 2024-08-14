package com.example.crudlibreria.models

class estado_Multa {
    val pendient=1
    val pendienteDesc="pendiente"
    val pago=2
    val pagoDesc="pago"

    val listaEstadoMulta= mutableListOf(
        pendienteDesc,pagoDesc
    )
    fun obtenerDescEstadoMulta(estadoMulta: Int):String{
        return when(estadoMulta){
            pendient->pendienteDesc
            pago->pagoDesc
            else->"este Estado de Multa  no existe"
        }
    }
    fun obtenerIntEstadoMulta(estadoMulta: String):Int{
        return  when(estadoMulta){
            pendienteDesc->pendient
            pagoDesc->pago
            else->0
        }
    }
}


