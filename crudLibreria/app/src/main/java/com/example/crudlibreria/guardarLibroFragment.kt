package com.example.crudlibreria

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.crudlibreria.config.config
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [guardarLibroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class guardarLibroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // definimos las variables del formulario
    private var id:Int=0 // el entero no se puede quedar nulo
    private lateinit var txtTitulo:EditText
    private lateinit var txtAutor:EditText
    private lateinit var txtIsbn:EditText
    private lateinit var txtGenero:EditText
    private lateinit var txtNum_ejem_disponible:EditText
    private lateinit var txtNum_ejem_ocupados:EditText


    //try-catch intente hacer una peticion si sale un error captura y se muestra un mesaje evitando que se cierre la aplicacion
    fun guardarLibro(){
        // esta clase para que cree o actualizar libro
        try {
           if(id==0){ // se crea un nuevo libro

               //se crea la peticion
               val request=object:StringRequest(
                   Request.Method.POST, //metodo de la peticion actualizar
                   config.urlLibro, //url de la peticion
                   Response.Listener{
                       //metodo que se ejecuta cuando la peticion es correcta
                                    Toast.makeText(
                                        context,"se Guardo Correctamente",
                                        Toast.LENGTH_LONG
                                    ).show()
                   },
                   Response.ErrorListener{
                       //metodo que se ejecuta cuando la peticion da error
                       Toast.makeText(
                           context,"error al guardar",
                           Toast.LENGTH_LONG
                       ).show()
                   }
               )

           }else{// se actualiza el libro

           }
        }
        catch (erro:Exception){ // esta variable captura el error

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guardar_libro, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment guardarLibroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            guardarLibroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}