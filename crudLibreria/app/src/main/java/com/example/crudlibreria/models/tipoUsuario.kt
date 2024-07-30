package com.example.crudlibreria.models

class tipoUsuario {

    val lector=1
    val lectorDesc="Lector"
    val bibliotecario=2
    val bibliotecarioDesc="Bibliotecario"
    val administrador=3
    val administradorDesc="Administrador"

    val listaTipoUsuario= mutableListOf(
        lectorDesc,bibliotecarioDesc,administradorDesc
    )
    fun obtenerDescTipoUsuario(tipoUsuario: Int):String{
       return when(tipoUsuario){
        lector->lectorDesc
        bibliotecario->bibliotecarioDesc
        administrador->administradorDesc
        else->"este tipo de usuario no existe"
       }
    }
    fun obtenerIntTipoUsuario(tipoUsuario: String):Int{
        return  when(tipoUsuario){
            lectorDesc->lector
            bibliotecarioDesc->bibliotecario
            administradorDesc->administrador
            else->0
        }
    }

}