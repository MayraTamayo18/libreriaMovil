package com.example.crudlibreria.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crudlibreria.R
import org.json.JSONArray
import org.json.JSONObject

class adapterMulta
    (
    var listMulta: JSONArray,
    var context: Context,

    ): RecyclerView.Adapter<adapterMulta.MyHolder>() {
    /*
    Se crea la clase Myholder
     */
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /*
        Dentro de la clase MyHolder se crea las variables
        y se asocian los objetos de la vista item
         */
        lateinit var lblUsuario_multado: TextView
        lateinit var lblValor_multa: TextView
        lateinit var btnEditar: Button
        lateinit var btnEliminar: Button

        init {
            lblUsuario_multado = itemView.findViewById(R.id.lblUsuario_multado)
            lblValor_multa = itemView.findViewById(R.id.lblValor_multa)
            btnEditar = itemView.findViewById(R.id.btnEditar)
            btnEliminar = itemView.findViewById(R.id.btnEliminar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterMulta.MyHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_multa, parent, false)
        return MyHolder(itemView)
    }

    //variable que almacena la función onclick nuevo que puse
    var onclick: ((JSONObject) -> Unit)? = null
    var onclickEliminar: ((JSONObject) -> Unit)? = null

    override fun onBindViewHolder(holder: adapterMulta.MyHolder, position: Int) {
        //obtener el registro
        val prestamo = listMulta.getJSONObject(position)
        //asignar valores
        /*
        Para obtener la información de un json dentro otro
        1 hay obtener el json y luego la información requerida
        2 dentro del json solicitar la información requerida
         */



        holder.lblUsuario_multado.text = prestamo.getInt("usuario_multado").toString()
        //var libro=prestamo.getJSONObject("libro_prestamo")
        holder.lblValor_multa.text = prestamo.getInt("valor_multa").toString()
        //holder.lblLibro_prestamo.text=libro.getString("titulo")


        //holder.lblLibro_prestamo.text=prestamo.getJSONObject("libro_prestamo").getString("titulo")


        //se crea la función del onclick nuevo que puse
        holder.btnEditar.setOnClickListener {
            onclick?.invoke(listMulta.getJSONObject(position))
        }

        holder.btnEliminar.setOnClickListener {
            onclickEliminar?.invoke(listMulta.getJSONObject(position))
        }
    }


    /*
    getItemCount: retorna el número de elementos
    de la lista
     */
    override fun getItemCount(): Int {
        return listMulta.length()
    }
}
