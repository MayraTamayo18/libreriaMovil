package com.example.crudlibreria

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibreria.adapter.adapterUsuario
import com.example.crudlibreria.config.config

//import com.example.crudlibreria.

/*
class mostrarUsuarioFragment : DialogFragment() {

    //private val onSubmitClickListener:()-> Unit

    private lateinit var binding :DialogInputBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding= DialogoInputBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
    }
    private lateinit var View: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        View= inflater.inflate(R.layout.fragment_mostrar_usuario, container, false)
        cargar_usuario()
        return View
    }


    fun cargar_usuario(){
        try {
            var request= JsonArrayRequest(
                Request.Method.GET,
                config.urlUsuario,
                null,
                {response->
                    var registro=response
                    //se crea y asocia una variable con el objeto de la vista
                    var recycler=View.findViewById<RecyclerView>(R.id.RVMostrarUsuario)
                    recycler.layoutManager= LinearLayoutManager(requireContext())
                    //se crea el adaptador
                    var adapterUsuario= adapterUsuario(registro,requireContext())
                    //acción cuando se hace click sobre el item nuevo que puse
                    //se asocia el adaptador con el objeto
                    recycler.adapter=adapterUsuario
                },
                { error->
                    Toast.makeText(context,"Error en la consulta", Toast.LENGTH_LONG).show()
                }
            )
            val queue= Volley.newRequestQueue(context)
            queue.add(request)
        }catch (e:Exception){

        }

    }

}
*/