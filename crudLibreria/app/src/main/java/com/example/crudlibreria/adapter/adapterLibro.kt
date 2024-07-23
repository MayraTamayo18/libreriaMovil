package com.example.crudlibreria.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crudlibreria.R
import com.example.crudlibreria.models.libro
import org.json.JSONArray

class adapterLibro
    (
    var listLibro: JSONArray,
    var context: Context
    ): RecyclerView.Adapter<adapterLibro.MyHolder>()
    {
        /*
        Se crea la clase Myholder
         */
        inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            /*
            Dentro de la clase MyHolder se crea las variables
            y se asocian los objetos de la vista item
             */
            lateinit var lblTitulo: TextView
            lateinit var lblAutor: TextView
            init{
                lblTitulo=itemView.findViewById(R.id.lblTitulo)
                lblAutor=itemView.findViewById(R.id.lblAutor)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterLibro.MyHolder {
            var itemView= LayoutInflater.from(context).inflate(R.layout.item_libros,parent,false)
            return MyHolder(itemView)
        }

        override fun onBindViewHolder(holder: adapterLibro.MyHolder, position: Int) {
            //obtener el registro
            val libro=listLibro.getJSONObject(position)
            //asignar valores
            holder.lblTitulo.text=libro.getString("titulo")
            holder.lblAutor.text=libro.getString("autor")
        }
        /*
        getItemCount: retorna el número de elementos
        de la lista
         */
        override fun getItemCount(): Int {
            return listLibro.length()
        }

    }
