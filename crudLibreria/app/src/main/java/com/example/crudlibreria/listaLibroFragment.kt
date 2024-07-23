package com.example.crudlibreria

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.window.application
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibreria.adapter.adapterLibro
import com.example.crudlibreria.config.config

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [listaLibroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class listaLibroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    //fun CambioLibroDetalle(view: View){
    //    var intent = Intent(application, detalleLibroFragment::class.java)
    //    startActivity(intent)
    //}

    private lateinit var View: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        View= inflater.inflate(R.layout.fragment_lista_libro, container, false)
        cargar_libro()
        return View
    }

    fun cargar_libro(){
        try {
            var request=JsonArrayRequest(
                Request.Method.GET,
                config.urlLibro,
                null,
                {response->
                    var registro=response
                    //se crea y asocia una variable con el objeto de la vista
                    var recycler=View.findViewById<RecyclerView>(R.id.RVLibros)
                    recycler.layoutManager= LinearLayoutManager(requireContext())
                    //se crea el adaptador
                    var adapterEmployed= adapterLibro(registro,requireContext())
                    //se asocia el adaptador con el objeto
                    recycler.adapter=adapterEmployed
                },
                { error->
                    Toast.makeText(context,"Error en la consulta",Toast.LENGTH_LONG).show()
                }
            )
            val queue= Volley.newRequestQueue(context)
            queue.add(request)
        }catch (e:Exception){

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment listaLibroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            listaLibroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

