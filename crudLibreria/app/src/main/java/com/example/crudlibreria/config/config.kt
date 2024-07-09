package com.example.crudlibreria.config

class config {
    /*
    static-> se ceran sin instancia llaman directamente a la calse y el valor es el mismo para todos
    se creean una variables staticas que no van a cambiar estas variables van a tener las url
    se crea una url static, para consultar sin instanciar
    metodo companion object sirve para almacenar las variables static
    */
    companion object{
        /*
        esta es solo  para el computador que tenga y este compartiendo el internet*/
       /*val urlBase="http://192.168.137.1:8000/api/v1/"*/

        /*url del mia*/val urlBase="http://192.168.137.148:8000/libreria/api/v1/"
        /*url del profesorval urlBase="http://10.192.80.151:8080/api/v1/"*/
        /*
      en el caso de que a el compurador donde estoy trabajando(andoidEstudio)me estan compartiendo la red wifi */
       /* val urlBase="http://10.192.80.150" este lo debo poner en mi computador personal */
        val urlLibro= urlBase+"libro/"

    }
}