package com.example.crudlibreria

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

import com.example.crudlibreria.models.tipoUsuario
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
    private lateinit var SpinnerTipoUsuario: Spinner
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
                    SpinnerTipoUsuario.setSelection(response.getInt("tipoUsuario")-1)
                   // SpinnerTipoUsuario.setText(response.getInt("tipoUsuario"))


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
                // se crea un objeto para el tipo de ususario
                val tipoUsuario=tipoUsuario()
                val  parametros= JSONObject()
                parametros.put("nombre",txtNombre.text.toString())
                parametros.put("direccion",txtDireccion.text.toString())
                parametros.put("correo",txtCorreo.text.toString())
                // para que almacene
                parametros.put("tipoUsuario",tipoUsuario.obtenerIntTipoUsuario(SpinnerTipoUsuario.selectedItem.toString()))

                var request= JsonObjectRequest(
                    Request.Method.POST, //metodo
                    config.urlUsuario, //ur
                    parametros,//datos de la peticion
                    {response->
                         Toast.makeText( context,"se guardor correctamente", Toast.LENGTH_SHORT).show()
                        // debe realizar la redireccion
                         val transaction=requireFragmentManager()
                             .beginTransaction()
                         var fragmento=listarUsuarioFragment()
                           transaction.replace(
                            R.id.fragmentContainerView,
                            fragmento).commit()
                         transaction.addToBackStack(null)

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
                // se crea un objeto para el tipo de ususario
                val tipoUsuario=tipoUsuario()
                parametros.put("nombre",txtNombre.text.toString())
                parametros.put("direccion",txtDireccion.text.toString())
                parametros.put("correo",txtCorreo.text.toString())
                parametros.put("tipoUsuario",tipoUsuario.obtenerIntTipoUsuario(SpinnerTipoUsuario.selectedItem.toString()))

                var request= JsonObjectRequest(
                    Request.Method.PUT, //metodo
                    config.urlUsuario + id + "/", //ur
                    parametros,//datos de la peticion
                    {response->
                        Toast.makeText( context,"se Actualizo correctamente", Toast.LENGTH_SHORT).show()
                        // debe realizar la redireccion
                         val transaction=requireFragmentManager()
                             .beginTransaction()
                         var fragmento=listarUsuarioFragment()
                         transaction.replace(
                             R.id.fragmentContainerView,
                             fragmento).commit()
                        transaction.addToBackStack(null)
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
        SpinnerTipoUsuario=view.findViewById(R.id.SpinnerTipoUsuario)

        btnGuardar=view.findViewById(R.id.btnGuardar)
        btnGuardar.setOnClickListener{
            guardarUsuario()

        }

        //boton volver
        var btnVolver: Button = view.findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            //crea la instancia del fragmentoPrincipal
            var fragmentPrincipal = listarUsuarioFragment()
            //trasaccion de fracmentos
            var transsaction = fragmentManager.beginTransaction()
            //reemplaza fragmento
            transsaction.replace(R.id.fragmentContainerView, fragmentPrincipal)

            transsaction.addToBackStack(null)
            //confirma los cambios
            transsaction.commit()
        }
        consultarUsuario()
        cargarFormulario()

        return view


    }
    // vamos a crear un metodo para cargar el formula de todo
    fun cargarFormulario(){
        caragarTipoUsuario()
    }
    // generar las lista del spinner
    fun caragarTipoUsuario(){
        val tipoUsuario= tipoUsuario()

        // creamos un pequeño adapter para saber como se van a mostrar los datos
        val adapterSpinner=ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            tipoUsuario.listaTipoUsuario
        )
        SpinnerTipoUsuario.adapter=adapterSpinner
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