package com.example.crudlibreria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibreria.config.config
import com.example.crudlibreria.models.usuario
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.Exception
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [guardarUsuarioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class guardarUsuarioFragment: Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // definimos las variables del formulario
    private var id:Int=0 // el entero no se puede quedar nulo
    private lateinit var txtNombre: EditText
    private lateinit var txtDireccion: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var tipoUsuario: Spinner
    private lateinit var btnGuardar: Button


    //try-catch intente hacer una peticion si sale un error captura y se muestra un mesaje evitando que se cierre la aplicacion

    //metodo encargado de traer la informacion del usuario
    fun consultarUsuario(){
        // solo se debe consultar si el id es diferente a vacio
        if(id!=0){
            var request= JsonObjectRequest(
                Request.Method.GET, //metodo
                config.urlUsuario + id, //ur
                null,//datos de la peticion
                {response->
                    //variable response contiene la respuesta de la api
                    //se convierte en json a un objeto tipo usuario
                    //generamos un objeto de la libreria gson
                    val gson= Gson()
                    //se convierte
                    val usuario: usuario =gson.fromJson(response.toString(), usuario::class.java)
                    // se modifica el atributo text de los campos con el valor del objeto
                    txtNombre.setText(response.getString("nombre"))
                    txtDireccion.setText(response.getString("direccion"))
                    txtCorreo.setText(response.getString("correo"))
                    //tipoUsuario.selectedItem=response.getInt("tipoUsuario")

                },//cuando la respuesta es correcta
                {error-> Toast.makeText(context,"Error al consultar", Toast.LENGTH_LONG).show() }//cuando es incorrecta
            )
            // se crea la cola del trabajo
            val queue= Volley.newRequestQueue(context)
            // se añade la peticion
            queue.add(request)
        }
    }

    fun guardarUsuario(){
        // esta clase para que cree o actualizar
        try {
            if(id==0){
                val  parametros= JSONObject()
                parametros.put("nombre",txtNombre.text.toString())
                parametros.put("direccion",txtDireccion.text.toString())
                parametros.put("correo",txtCorreo.text.toString())
                parametros.put("tipoUsuario",tipoUsuario.selectedItem)

                var request= JsonObjectRequest(
                    Request.Method.POST, //metodo
                    config.urlUsuario, //ur
                    parametros,//datos de la peticion
                    {response->
                        Toast.makeText( context,"se guardor correctamente", Toast.LENGTH_SHORT).show()

                        // debe realizar la redireccion
                       // val transaction=requireFragmentManager()
                       //     .beginTransaction()
                       // var fragmento=listaUsuarioFragment()
                       // transaction.replace(
                       //     R.id.fragmentContainerView,
                       //     fragmento).commit()
                        //transaction.addToBackStack(null)

                    },//cuando la respuesta es correcta

                    {error-> Toast.makeText(context, "se genero un error", Toast.LENGTH_LONG).show() }//cuando es incorrecta
                )
                // se crea la cola del trabajo
                val queue= Volley.newRequestQueue(context)
                // se añade la peticion
                queue.add(request)

            }else{ // se actualiza el usuario
                //editar
                val  parametros= JSONObject()
                parametros.put("nombre",txtNombre.text.toString())
                parametros.put("direccion",txtDireccion.text.toString())
                parametros.put("correo",txtCorreo.text.toString())
                parametros.put("tipoUsuario",tipoUsuario.selectedItem)

                var request= JsonObjectRequest(
                    Request.Method.PUT, //metodo
                    config.urlUsuario + id + "/", //ur
                    parametros,//datos de la peticion
                    {response->
                        Toast.makeText( context,"se Actualizo correctamente", Toast.LENGTH_SHORT).show()
                        // debe realizar la redireccion
                        // val transaction=requireFragmentManager()
                        //     .beginTransaction()
                        // var fragmento=listaUsuarioFragment()
                        // transaction.replace(
                        //     R.id.fragmentContainerView,
                        //     fragmento).commit()
                        //transaction.addToBackStack(null)
                    },//cuando la respuesta es correcta

                    {error-> Toast.makeText(context, "se genero un error", Toast.LENGTH_LONG).show() }//cuando es incorrecta
                )
                //acción cuando se hace click sobre el item nuevo que puse

                // se crea la cola del trabajo
                val queue= Volley.newRequestQueue(context)
                // se añade la peticion
                queue.add(request)
            }
        }
        catch (erro: Exception){ // esta variable captura el error

        }
        //mensaje de que el registro se guardo

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            id=it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_guardar_usuario, container, false)
        txtNombre=view.findViewById(R.id.textNombre)
        txtDireccion=view.findViewById(R.id.textDireccion)
        txtCorreo=view.findViewById(R.id.textCorreo)
        tipoUsuario=view.findViewById(R.id.tipoUsuario)

        btnGuardar=view.findViewById(R.id.btnGuardar)
        btnGuardar.setOnClickListener{
            guardarUsuario()

        }
        consultarUsuario()
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment guardarUsuarioFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            guardarUsuarioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}