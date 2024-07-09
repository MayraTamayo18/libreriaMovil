package com.example.crudlibreria

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibreria.config.config
import com.example.crudlibreria.models.libro
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [detalleLibroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class detalleLibroFragment : Fragment() {
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

    // definir las variables

    private lateinit var lblTitulo: TextView
    private lateinit var lblAutor: TextView
    private lateinit var lblIsbn: TextView
    private lateinit var lblGenero: TextView
    private lateinit var lblNum_ejem_disponible: TextView
    private lateinit var lblNum_ejem_ocupados: TextView

    private lateinit var btnEditar:Button
    private lateinit var btnEliminar:Button



    // se asigna un id temporal existente
    private var id:Int=7 // el entero no se puede quedar nulo

    fun consultarLibro(){
        // solo se debe consultar si el id es diferente a vacio
        if(id!=0){
            var request= JsonObjectRequest(
                Request.Method.GET, //metodo
                config.urlLibro + id, //ur
                null,//datos de la peticion
                {response->
                    //variable response contiene la respuesta de la api
                    //se convierte en json a un objeto tipo libro
                    //generamos un objeto de la libreria gson
                    val gson= Gson()
                    //se convierte
                    val libro: libro =gson.fromJson(response.toString(), libro::class.java)
                    // se modifica el atributo text de los campos con el valor del objeto
                    lblTitulo.setText(response.getString("titulo"))
                    lblAutor.setText(response.getString("autor"))
                    lblIsbn.setText(response.getString("isbn"))
                    lblGenero.setText(response.getString("genero"))
                    lblNum_ejem_disponible.setText(response.getInt("num_ejem_disponible").toString())
                    lblNum_ejem_ocupados.setText(response.getInt("num_ejem_ocupados").toString())

                },//cuando la respuesta es correcta
                {error-> Toast.makeText(context,"Error al consultar", Toast.LENGTH_LONG).show() }//cuando es incorrecta
            )
            // se crea la cola del trabajo
            val queue= Volley.newRequestQueue(context)
            // se añade la peticion
            queue.add(request)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_detalle_libro, container, false)
        lblTitulo=view.findViewById(R.id.lblTitulo)
        lblAutor=view.findViewById(R.id.lblAutor)
        lblIsbn=view.findViewById(R.id.lblIsbn)
        lblGenero=view.findViewById(R.id.lblGenero)
        lblNum_ejem_disponible=view.findViewById(R.id.lblNum_ejem_disponible)
        lblNum_ejem_ocupados=view.findViewById(R.id.lblNum_ejem_ocupados)

        //se ejecuta la consulta
        consultarLibro()

        btnEditar=view.findViewById(R.id.btnEditar)
        btnEditar.setOnClickListener{editarLibro()}
        btnEliminar= view.findViewById(R.id.btnEliminar)
        btnEliminar.setOnClickListener{eliminarLibro()}

        return view
    }
    //se crea el metodo ´para editarlinro
    fun editarLibro(){

    }
    fun eliminarLibro(){

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment detalleLibroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            detalleLibroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}