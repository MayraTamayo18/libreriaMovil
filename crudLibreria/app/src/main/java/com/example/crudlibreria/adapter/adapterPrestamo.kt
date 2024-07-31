package com.example.crudlibreria.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibreria.R
import com.example.crudlibreria.config.config
import com.example.crudlibreria.models.tipoUsuario
import com.example.crudlibreria.models.usuario
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class adapterPrestamo
    (
    var listPrestamo: JSONArray,
    var context: Context,

    ): RecyclerView.Adapter<adapterPrestamo.MyHolder>()
{
    /*
    Se crea la clase Myholder
     */
    inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        /*
        Dentro de la clase MyHolder se crea las variables
        y se asocian los objetos de la vista item
         */
        lateinit var lblUsuario_prestamo: TextView
        lateinit var lblLibro_prestamo: TextView
        lateinit var btnEditar: Button
        lateinit var btnEliminar: Button

        init{
            lblUsuario_prestamo=itemView.findViewById(R.id.lblUsuario_prestamo)
            lblLibro_prestamo=itemView.findViewById(R.id.lblLibro_prestamo)
            btnEditar=itemView.findViewById(R.id.btnEditar)
            btnEliminar=itemView.findViewById(R.id.btnEliminar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterPrestamo.MyHolder {
        var itemView= LayoutInflater.from(context).inflate(R.layout.item_prestamo,parent,false)
        return MyHolder(itemView)
    }

    //variable que almacena la función onclick nuevo que puse
    var onclick:((JSONObject)->Unit)?=null
    var onclickEliminar:((JSONObject)->Unit)?=null

    override fun onBindViewHolder(holder: adapterPrestamo.MyHolder, position: Int) {
        //obtener el registro
        val prestamo=listPrestamo.getJSONObject(position)
        //asignar valores
        /*
        Para obtener la información de un json dentro otro
        1 hay obtener el json y luego la información requerida
        2 dentro del json solicitar la información requerida
         */



        holder.lblUsuario_prestamo.text= prestamo.getInt("usuario_prestamo").toString()
        //var libro=prestamo.getJSONObject("libro_prestamo")
        holder.lblLibro_prestamo.text= prestamo.getInt("libro_prestamo").toString()
        //holder.lblLibro_prestamo.text=libro.getString("titulo")


        //holder.lblLibro_prestamo.text=prestamo.getJSONObject("libro_prestamo").getString("titulo")


        //se crea la función del onclick nuevo que puse
        holder.btnEditar.setOnClickListener{
            onclick?.invoke(listPrestamo.getJSONObject(position))
        }

        holder.btnEliminar.setOnClickListener{
            onclickEliminar?.invoke(listPrestamo.getJSONObject(position))
        }
    }



    /*
    getItemCount: retorna el número de elementos
    de la lista
     */
    override fun getItemCount(): Int {
        return listPrestamo.length()
    }

}