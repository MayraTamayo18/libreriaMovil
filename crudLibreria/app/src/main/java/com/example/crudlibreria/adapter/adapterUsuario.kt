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

class adapterUsuario
    (
    var listUsuario: JSONArray,
    var context: Context,

    ): RecyclerView.Adapter<adapterUsuario.MyHolder>()
{
    /*
    Se crea la clase Myholder
     */
    inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        /*
        Dentro de la clase MyHolder se crea las variables
        y se asocian los objetos de la vista item
         */
        lateinit var lblNombre: TextView
        lateinit var lblCorreo: TextView
        lateinit var btnEditar: Button
        lateinit var  btnEliminar: Button

        init{
            lblNombre=itemView.findViewById(R.id.lblNombre)
            lblCorreo=itemView.findViewById(R.id.lblCorreo)
            btnEditar=itemView.findViewById(R.id.btnEditar)
            btnEliminar=itemView.findViewById(R.id.btnEliminar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterUsuario.MyHolder {
        var itemView= LayoutInflater.from(context).inflate(R.layout.item_usuario,parent,false)
        return MyHolder(itemView)
    }

    //variable que almacena la función onclick nuevo que puse
    var onclick:((JSONObject)->Unit)?=null
    var onclickEliminar:((JSONObject)->Unit)?=null

    override fun onBindViewHolder(holder: adapterUsuario.MyHolder, position: Int) {
        //obtener el registro
        val libro=listUsuario.getJSONObject(position)
        //asignar valores
        holder.lblNombre.text=libro.getString("nombre")
        holder.lblCorreo.text=libro.getString("correo")


        //se crea la función del onclick nuevo que puse
        holder.btnEditar.setOnClickListener{
            onclick?.invoke(listUsuario.getJSONObject(position))
        }

        holder.btnEliminar.setOnClickListener{
            onclickEliminar?.invoke(listUsuario.getJSONObject(position))
        }
    }

    /*
    getItemCount: retorna el número de elementos
    de la lista
     */
    override fun getItemCount(): Int {
        return listUsuario.length()
    }

}