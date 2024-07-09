package com.example.crudlibreria

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibreria.config.config
import com.example.crudlibreria.models.libro
import com.google.gson.Gson
//import com.google.gson.JsonObject //genera error en el put cun¿ando trabajan con json
import org.json.JSONObject //esta es la que se trabaja para json
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
    private var id:Int=7 // el entero no se puede quedar nulo
    private lateinit var txtTitulo:EditText
    private lateinit var txtAutor:EditText
    private lateinit var txtIsbn:EditText
    private lateinit var txtGenero:EditText
    private lateinit var txtNum_ejem_disponible:EditText
    private lateinit var txtNum_ejem_ocupados:EditText
    private lateinit var btnGuardar:Button



    //try-catch intente hacer una peticion si sale un error captura y se muestra un mesaje evitando que se cierre la aplicacion
    /*
    Request es la peticion que hace la api
    StringRequest=responde un string
    JsonRequest=responde un json
    JsonArrayRequest= responde un arreglo de json

    * */
    //metodo encargado de traer la informacion del libro
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
                   val gson=Gson()
                     //se convierte
                   val libro: libro =gson.fromJson(response.toString(),libro::class.java)
                   // se modifica el atributo text de los campos con el valor del objeto
                   txtTitulo.setText(response.getString("titulo"))
                   txtAutor.setText(response.getString("autor"))
                   txtIsbn.setText(response.getString("isbn"))
                   txtGenero.setText(response.getString("genero"))
                   txtNum_ejem_disponible.setText(response.getInt("num_ejem_disponible").toString())
                   txtNum_ejem_ocupados.setText(response.getInt("num_ejem_ocupados").toString())

                 },//cuando la respuesta es correcta
                 {error->Toast.makeText(context,"Error al consultar",Toast.LENGTH_LONG).show() }//cuando es incorrecta
             )
             // se crea la cola del trabajo
             val queue=Volley.newRequestQueue(context)
             // se añade la peticion
             queue.add(request)
         }
    }

    fun guardarLibro(){
        // esta clase para que cree o actualizar libro
        try {
           if(id==0){ // se crea un nuevo libro
/*
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
               // vamos a crear una variable que almacene todos los datos del metodo
               {
                   override fun getParams(): MutableMap<String, String>? {
                       var parametros=HashMap<String, String>()
                       //creamos un parametro por cada dato que requiere

                       parametros.put("titulo",txtTitulo.text.toString())
                       parametros.put("autor",txtAutor.text.toString())
                       parametros.put("isbn",txtIsbn.text.toString())
                       parametros.put("genero",txtGenero.text.toString())
                       parametros.put("num_ejem_disponible",txtNum_ejem_disponible.text.toString())
                       parametros.put("num_ejem_ocupados",txtNum_ejem_ocupados.text.toString())

                       return parametros
                   }
               }

 */
              val  parametros=JSONObject()
               parametros.put("titulo",txtTitulo.text.toString())
               parametros.put("autor",txtAutor.text.toString())
               parametros.put("isbn",txtIsbn.text.toString())
               parametros.put("genero",txtGenero.text.toString())
               parametros.put("num_ejem_disponible",txtNum_ejem_disponible.text.toString())
               parametros.put("num_ejem_ocupados",txtNum_ejem_ocupados.text.toString())

               /*val  parametros=JSONObject()
               parametros.put("titulo",txtTitulo.text.toString())
               parametros.put("nombre_autor",txtAutor.text.toString())
               parametros.put("isbn",txtIsbn.text.toString())
               parametros.put("genero",txtGenero.text.toString())
               parametros.put("num_ejem_disponibles",txtNum_ejem_disponible.text.toString())
               parametros.put("num_ejem_ocupados",txtNum_ejem_ocupados.text.toString())*/

               var request= JsonObjectRequest(
                   Request.Method.POST, //metodo
                   config.urlLibro, //ur
                   parametros,//datos de la peticion
                   {response->Toast.makeText( context,"se guardor correctamente", Toast.LENGTH_SHORT).show() },//cuando la respuesta es correcta

                   {error->Toast.makeText(context, "se genero un error", Toast.LENGTH_LONG).show() }//cuando es incorrecta
               )


               // se crea la cola del trabajo
               val queue=Volley.newRequestQueue(context)
               // se añade la peticion
               queue.add(request)

           }else{ // se actualiza el libro
               //editar
               val  parametros=JSONObject()
               parametros.put("titulo",txtTitulo.text.toString())
               parametros.put("autor",txtAutor.text.toString())
               parametros.put("isbn",txtIsbn.text.toString())
               parametros.put("genero",txtGenero.text.toString())
               parametros.put("num_ejem_disponible",txtNum_ejem_disponible.text.toString())
               parametros.put("num_ejem_ocupados",txtNum_ejem_ocupados.text.toString())

               var request= JsonObjectRequest(
                   Request.Method.PUT, //metodo
                   config.urlLibro + id + "/", //ur
                   parametros,//datos de la peticion
                   {response->Toast.makeText( context,"se Actualizo correctamente", Toast.LENGTH_SHORT).show() },//cuando la respuesta es correcta

                   {error->Toast.makeText(context, "se genero un error", Toast.LENGTH_LONG).show() }//cuando es incorrecta
               )

               // se crea la cola del trabajo
               val queue=Volley.newRequestQueue(context)
               // se añade la peticion
               queue.add(request)
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
        var view= inflater.inflate(R.layout.fragment_guardar_libro, container, false)
        txtTitulo=view.findViewById(R.id.textTitulo)
        txtAutor=view.findViewById(R.id.textAutor)
        txtIsbn=view.findViewById(R.id.textIsbn)
        txtGenero=view.findViewById(R.id.textgenero)
        txtNum_ejem_disponible=view.findViewById(R.id.textNum_ejem_disponible)
        txtNum_ejem_ocupados=view.findViewById(R.id.textNum_ejem_ocupados)
        btnGuardar=view.findViewById(R.id.btnGuardar)
        btnGuardar.setOnClickListener{
            guardarLibro()

        }
        consultarLibro()
        return view
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