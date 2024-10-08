package com.example.crudlibreria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibreria.adapter.adapterMulta
import com.example.crudlibreria.config.config
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class listaMultaFragment : Fragment() {
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


    private lateinit var View: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        View= inflater.inflate(R.layout.fragment_listar_multa, container, false)
        cargar_multa()
        var btnAgregar=View.findViewById<FloatingActionButton>(R.id.btnAgregar)
        btnAgregar.setOnClickListener{AgregarMulta()}
        return View
    }


    fun cargar_multa(){
        try {
            var request= JsonArrayRequest(
                Request.Method.GET,
                config.urlMulta,
                null,
                {response->
                    var registro=response
                    //se crea y asocia una variable con el objeto de la vista
                    var recycler=View.findViewById<RecyclerView>(R.id.RVMulta)
                    recycler.layoutManager= LinearLayoutManager(requireContext())
                    //se crea el adaptador
                    var adapterMulta=adapterMulta(registro,requireContext())

                    //acción cuando se hace click sobre el item nuevo que puse
                    adapterMulta.onclick={
                        //cambio de fragmanto desde otro nuevo
                        val bundle= Bundle()
                        bundle.putInt("id",it.getInt("id"))
                        val transaction=requireFragmentManager()
                            .beginTransaction()
                        var fragmento=guardarMultaFragment()
                        fragmento.arguments=bundle
                        transaction.replace(
                            R.id.fragmentContainerView,
                            fragmento).commit()
                        transaction.addToBackStack(null)
                    }
                    adapterMulta.onclickEliminar={
                        // mensaje de que si deseas eliminar
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setMessage("Desea eliminar este Registro")
                            .setPositiveButton("Si") { dialog, id ->
                                // START THE GAME! eliminar funcion  Llmara la funcion  eliminar()
                                EliminarMulta(it.getInt("id"))
                                // redirije a la vista de listar usuario recargada pero aun me aparece un error
                                val transaction=requireFragmentManager()
                                    .beginTransaction()
                                var fragmento=listaMultaFragment()
                                transaction.replace(
                                    R.id.fragmentContainerView,
                                    fragmento).commit()
                                transaction.addToBackStack(null)
                            }
                            .setNegativeButton("No") { dialog, id ->
                                // User cancelled the dialog.
                            }
                        // Create the AlertDialog object and return it.
                        builder.create()
                        builder.show()
                    }
                    //se asocia el adaptador con el objeto
                    recycler.adapter=adapterMulta
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
// crear la funcion de eliminar usuario que es la misma de cargar usuario con el delete

    fun EliminarMulta(id: Int){
        try {
            /*
            JsonArrayRequest=arreglo json
            JsonObjectRequest=Json
            StringRequest=texto, incluyendo ""
             */
            var request= JsonObjectRequest(
                Request.Method.DELETE,
                config.urlMulta+id+"/",
                null,
                {response->
                    cargar_multa()
                    Toast.makeText(context,"se elimino correctamente ", Toast.LENGTH_LONG).show()
                },
                { error->
                    Toast.makeText(context,"Error", Toast.LENGTH_LONG).show()
                }
            )
            val queue= Volley.newRequestQueue(context)
            queue.add(request)
        }catch (e:Exception){

        }

    }
    fun AgregarMulta(){
        val transaction=requireFragmentManager()
            .beginTransaction()
        var fragmento=guardarMultaFragment()
        transaction.replace(
            R.id.fragmentContainerView,
            fragmento).commit()
        transaction.addToBackStack(null)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment listaUsuarioFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            listaPrestamoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
