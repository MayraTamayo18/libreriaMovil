package com.example.crudlibreria.models

class tipoEstado {
    val Prestado=1
    val prestadoDesc="Prestamo"
    val Entregado=2
    val entregadoDesc="Entregado"
    val Cancelado=3
    val canceladoDesc="Cancelado"

    val listaTipoEstado= mutableListOf(
        prestadoDesc,entregadoDesc,canceladoDesc
    )
    fun obtenerDescTipoEstado(tipoEstado: Int):String{
        return when(tipoEstado){
            Prestado->prestadoDesc
            Entregado->entregadoDesc
            Cancelado->canceladoDesc
            else->"este tipo de estado no existe"
        }
    }
    fun obtenerIntTipoEstado(tipoEstado: String):Int{
        return  when(tipoEstado){
            prestadoDesc->Prestado
            entregadoDesc->Entregado
            canceladoDesc->Cancelado
            else->0
        }
    }
}