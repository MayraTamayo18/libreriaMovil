package com.example.crudlibreria

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibreria.config.config
import com.example.crudlibreria.models.prestamo
import com.example.crudlibreria.models.tipoEstado
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [guardarPrestamoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class guardarPrestamoFragment: Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // definimos las variables del formulario
    private var id:Int=0 // el entero no se puede quedar nulo
    private lateinit var txtFecha_prestamo: EditText
    private lateinit var txtFecha_devolucion: EditText
    private lateinit var SpinnerEstdo: Spinner
    private lateinit var txtUsuario_prestamo: EditText
    private lateinit var txtLibro_prestamo: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCalendario: Button
    private lateinit var btnCalendario2: Button
    private lateinit var btnBuscarUsuario: Button
    private lateinit var btnBuscarLibro: Button




    //try-catch intente hacer una peticion si sale un error captura y se muestra un mesaje evitando que se cierre la aplicacion

    //metodo encargado de traer la informacion del usuario
    fun consultarPrestamo(){
        // solo se debe consultar si el id es diferente a vacio
        if(id!=0){
            var request= JsonObjectRequest(
                Request.Method.GET, //metodo
                config.urlPrestamo + id, //ur
                null,//datos de la peticion
                {response->
                    //variable response contiene la respuesta de la api
                    //se convierte en json a un objeto tipo usuario
                    //generamos un objeto de la libreria gson
                    val gson= Gson()
                    //se convierte
                    val prestamo: prestamo =gson.fromJson(response.toString(), prestamo::class.java)
                    // se modifica el atributo text de los campos con el valor del objeto
                    txtFecha_prestamo.setText(response.getString("fecha_prestamo"))
                    txtFecha_devolucion.setText(response.getString("fecha_devolucion"))
                    SpinnerEstdo.setSelection(response.getInt("Estado")-1)
                    txtUsuario_prestamo.setText(response.getInt("usuario_prestamo").toString())
                    txtLibro_prestamo.setText(response.getInt("libro_prestamo").toString())


                },//cuando la respuesta es correcta
                {error-> Toast.makeText(context,"Error al consultar", Toast.LENGTH_LONG).show() }//cuando es incorrecta
            )
            // se crea la cola del trabajo
            val queue= Volley.newRequestQueue(context)
            // se añade la peticion
            queue.add(request)
        }
    }
    // funcion para mostrar el calendario
    fun mostrarCalendario(text: EditText){
        //Toast.makeText(requireContext(),"",Toast.LENGTH_LONG).show()
        // Obtener la fecha actual
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Crear el DatePickerDialog calendario
        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // Formatear la fecha seleccionada
            val selectedDate = Calendar.getInstance().apply {
                set(selectedYear, selectedMonth, selectedDay)
            }
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            text.setText(formattedDate)
            // Mostrar la fecha seleccionada en el TextView
            //txtFecha_prestamo.setText(formattedDate)
            //retornar la fecha formateada setonclikListener resivir esa fecha para mostrarla en el cuadro de texto de la vista

        }, year, month, day)

        // Mostrar el DatePickerDialog
        datePickerDialog.show()
    }
//pendiente
    /*fun mostrarUsuario(fragmentManager: FragmentManager) {
        val usuarioListFragment = listarUsuarioFragment()

        val builder = AlertDialog.Builder(fragmentManager.findFragmentById(R.id.fragmentContainerView)!!.requireContext())
        builder.setTitle("lista de usuarios")
            .setView(R.layout.fragment_mostrar_usuario) // Set custom layout to Dialog
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()

        // Show the dialog
        dialog.show()

        val fragmentContainer = dialog.findViewById<ViewGroup>(android.R.id.content)
        if (fragmentContainer != null) {
            fragmentManager.beginTransaction()
                .replace(fragmentContainer.id, usuarioListFragment)
                .commit()
        }
    }*/

    fun guardarPrestamo(){
        // esta clase para que cree o actualizar
        try {
            if(id==0){
                // se crea un objeto para el tipo de ususario
                val tipoEstado= tipoEstado()
                val  parametros= JSONObject()
                parametros.put("fecha_prestamo",txtFecha_prestamo.text.toString())
                parametros.put("fecha_devolucion",txtFecha_devolucion.text.toString())
                // para que almacene
                parametros.put("Estado",tipoEstado.obtenerIntTipoEstado(SpinnerEstdo.selectedItem.toString()))
                parametros.put("usuario_prestamo",txtUsuario_prestamo.text.toString())
                parametros.put("libro_prestamo",txtLibro_prestamo.text.toString())



                var request= JsonObjectRequest(
                    Request.Method.POST, //metodo
                    config.urlPrestamo, //ur
                    parametros,//datos de la peticion
                    {response->
                        Toast.makeText( context,"se guardor correctamente", Toast.LENGTH_SHORT).show()
                        // debe realizar la redireccion
                        val transaction=requireFragmentManager()
                            .beginTransaction()
                        var fragmento=listaPrestamoFragment()
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
                val tipoEstado= tipoEstado()//nombre del modelo
                parametros.put("fecha_prestamo",txtFecha_prestamo.text.toString())
                parametros.put("fecha_devolucion",txtFecha_devolucion.text.toString())
                parametros.put("Estado",tipoEstado.obtenerIntTipoEstado(SpinnerEstdo.selectedItem.toString()))
                parametros.put("usuario_prestamo",txtUsuario_prestamo.text.toString())
                parametros.put("libro_prestamo",txtLibro_prestamo.text.toString())



                var request= JsonObjectRequest(
                    Request.Method.PUT, //metodo
                    config.urlPrestamo + id + "/", //ur
                    parametros,//datos de la peticion
                    {response->
                        Toast.makeText( context,"se Actualizo correctamente", Toast.LENGTH_SHORT).show()
                        // debe realizar la redireccion
                        val transaction=requireFragmentManager()
                            .beginTransaction()
                        var fragmento=listaPrestamoFragment()
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
        var view= inflater.inflate(R.layout.fragment_guardar_prestamo, container, false)
        txtFecha_prestamo=view.findViewById(R.id.textFecha_prestamo)
        txtFecha_devolucion=view.findViewById(R.id.textFecha_devolucion)
        SpinnerEstdo=view.findViewById(R.id.SpinnerEstado)
        txtUsuario_prestamo=view.findViewById(R.id.textUsuario_prestamo)
        txtLibro_prestamo=view.findViewById(R.id.textLibro_prestamo)




        btnGuardar=view.findViewById(R.id.btnGuardar)
        btnGuardar.setOnClickListener{
            guardarPrestamo()

        }
        btnCalendario=view.findViewById(R.id.btnCalendario)
        btnCalendario.setOnClickListener{
            mostrarCalendario(txtFecha_prestamo)

        }

        btnCalendario2=view.findViewById(R.id.btnCalendario2)
        btnCalendario2.setOnClickListener{
            mostrarCalendario(txtFecha_devolucion)
        }

        //quedo pendiente
        /*btnBuscarUsuario=view.findViewById(R.id.btnBuscarUsuario)
        btnBuscarUsuario.setOnClickListener{
            mostrarUsuario(parentFragmentManager)
        }*/

        //boton volver
        var btnVolver: Button = view.findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager
            //crea la instancia del fragmentoPrincipal
            var fragmentPrincipal = listaPrestamoFragment()
            //trasaccion de fracmentos
            var transsaction = fragmentManager.beginTransaction()
            //reemplaza fragmento
            transsaction.replace(R.id.fragmentContainerView, fragmentPrincipal)

            transsaction.addToBackStack(null)
            //confirma los cambios
            transsaction.commit()
        }
        consultarPrestamo()
        cargarFormulario()

        return view


    }
    // vamos a crear un metodo para cargar el formula de todo
    fun cargarFormulario(){
        caragarTipoEstado()
    }
    // generar las lista del spinner
    fun caragarTipoEstado(){
        val tipoEstado= tipoEstado()

        // creamos un pequeño adapter para saber como se van a mostrar los datos
        val adapterSpinner= ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            tipoEstado.listaTipoEstado
        )
        SpinnerEstdo.adapter=adapterSpinner
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