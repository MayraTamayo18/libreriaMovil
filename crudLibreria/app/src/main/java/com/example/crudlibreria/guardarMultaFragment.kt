package com.example.crudlibreria

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibreria.config.config
import com.example.crudlibreria.models.estado_Multa
import com.example.crudlibreria.models.prestamo
import com.example.crudlibreria.models.tipoEstado
import com.example.crudlibreria.models.usuario
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
 * Use the [detalleMultaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class guardarMultaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    // definir las variables
    private var id: Int = 0 // el entero no se puede quedar nulo
    private lateinit var txtFecha_multa: EditText
    private lateinit var txtUsuario_multado: EditText
    private lateinit var txtPrestamo: EditText
    private lateinit var txtValor_multa: EditText
    private lateinit var SpinnerEstdo_multa: Spinner
    private lateinit var btnGuardar: Button
    private lateinit var btnCalendario: Button
    private lateinit var btnBuscarUsuario: Button
    private lateinit var btnBuscarPrestamo: Button


    //try-catch intente hacer una peticion si sale un error captura y se muestra un mesaje evitando que se cierre la aplicacion

    //metodo encargado de traer la informacion del usuario
    fun consultarMulta() {
        // solo se debe consultar si el id es diferente a vacio
        if (id != 0) {
            var request = JsonObjectRequest(
                Request.Method.GET, //metodo
                config.urlMulta + id, //ur
                null,//datos de la peticion
                { response ->
                    //variable response contiene la respuesta de la api
                    //se convierte en json a un objeto tipo usuario
                    //generamos un objeto de la libreria gson
                    val gson = Gson()
                    //se convierte
                    val prestamo: prestamo =
                        gson.fromJson(response.toString(), prestamo::class.java)
                    // se modifica el atributo text de los campos con el valor del objeto
                    txtFecha_multa.setText(response.getString("fecha_multa"))
                    txtUsuario_multado.setText(response.getInt("usuario_multado").toString())
                    txtPrestamo.setText(response.getInt("prestamo").toString())
                    txtValor_multa.setText(response.getInt("valor_multa"))
                    SpinnerEstdo_multa.setSelection(response.getInt("estado_multa") - 1)


                },//cuando la respuesta es correcta
                { error ->
                    Toast.makeText(context, "Error al consultar", Toast.LENGTH_LONG).show()
                }//cuando es incorrecta
            )
            // se crea la cola del trabajo
            val queue = Volley.newRequestQueue(context)
            // se añade la peticion
            queue.add(request)
        }
    }

    // funcion para mostrar el calendario
    fun mostrarCalendario(text: EditText) {
        //Toast.makeText(requireContext(),"",Toast.LENGTH_LONG).show()
        // Obtener la fecha actual
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Crear el DatePickerDialog calendario
        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
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

    fun guardarMulta() {
        // esta clase para que cree o actualizar
        try {
            if (id == 0) {
                // se crea un objeto para el tipo de ususario
                val estado_multa = estado_Multa()
                val parametros = JSONObject()
                parametros.put("fecha_multa", txtFecha_multa.text.toString())
                // para que almacene
                parametros.put("usuario_multado", txtUsuario_multado.text.toString())
                parametros.put("prestamo", txtPrestamo.text.toString())
                parametros.put("valor_multa", txtValor_multa.text.toString())
                parametros.put(
                    "estado_multa",
                    estado_multa.obtenerIntEstadoMulta(SpinnerEstdo_multa.selectedItem.toString())
                )


                var request = JsonObjectRequest(
                    Request.Method.POST, //metodo
                    config.urlMulta, //ur
                    parametros,//datos de la peticion
                    { response ->
                        Toast.makeText(context, "se guardor correctamente", Toast.LENGTH_SHORT)
                            .show()
                        // debe realizar la redireccion
                        val transaction = requireFragmentManager()
                            .beginTransaction()
                        var fragmento = listaMultaFragment()
                        transaction.replace(
                            R.id.fragmentContainerView,
                            fragmento
                        ).commit()
                        transaction.addToBackStack(null)

                    },//cuando la respuesta es correcta

                    { error ->
                        Toast.makeText(context, "se genero un error", Toast.LENGTH_LONG).show()
                    }//cuando es incorrecta
                )
                // se crea la cola del trabajo
                val queue = Volley.newRequestQueue(context)
                // se añade la peticion
                queue.add(request)

            } else { // se actualiza el usuario
                //editar
                val parametros = JSONObject()
                // se crea un objeto para el tipo de ususario
                val estado_Multa = estado_Multa()//nombre del modelo
                parametros.put("fecha_multa", txtFecha_multa.text.toString())
                parametros.put("usuario_multado", txtUsuario_multado.text.toString())
                parametros.put("prestamo", txtPrestamo.text.toString())
                parametros.put("valor_multa", txtValor_multa.text.toString())
                parametros.put(
                    "estado_multa",
                    estado_Multa.obtenerIntEstadoMulta(SpinnerEstdo_multa.selectedItem.toString())
                )


                var request = JsonObjectRequest(
                    Request.Method.PUT, //metodo
                    config.urlMulta + id + "/", //ur
                    parametros,//datos de la peticion
                    { response ->
                        Toast.makeText(context, "se Actualizo correctamente", Toast.LENGTH_SHORT)
                            .show()
                        // debe realizar la redireccion
                        val transaction = requireFragmentManager()
                            .beginTransaction()
                        var fragmento = listaMultaFragment()
                        transaction.replace(
                            R.id.fragmentContainerView,
                            fragmento
                        ).commit()
                        transaction.addToBackStack(null)
                    },//cuando la respuesta es correcta

                    { error ->
                        Toast.makeText(context, "se genero un error", Toast.LENGTH_LONG).show()
                    }//cuando es incorrecta
                )
                //acción cuando se hace click sobre el item nuevo que puse

                // se crea la cola del trabajo
                val queue = Volley.newRequestQueue(context)
                // se añade la peticion
                queue.add(request)
            }
        } catch (erro: Exception) { // esta variable captura el error

        }
        //mensaje de que el registro se guardo

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            id = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_guardar_multa, container, false)
        txtFecha_multa = view.findViewById(R.id.textFecha_multa)
        txtUsuario_multado = view.findViewById(R.id.textUsuario_multado)
        txtPrestamo = view.findViewById(R.id.textPrestamo)
        txtValor_multa = view.findViewById(R.id.textValor_multa)
        SpinnerEstdo_multa = view.findViewById(R.id.SpinnerEstado_multa)





        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnGuardar.setOnClickListener {
            guardarMulta()

        }
        btnCalendario = view.findViewById(R.id.btnCalendario)
        btnCalendario.setOnClickListener {
            mostrarCalendario(txtFecha_multa)

        }


        //quedo pendiente
        /*btnBuscarUsuario=view.findViewById(R.id.btnBuscarUsuario)
        btnBuscarUsuario.setOnClickListener{
            mostrarUsuario(parentFragmentManager)
        }*/

        //boton volver
        var btnVolver: Button = view.findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            //crea la instancia del fragmentoPrincipal
            var fragmentPrincipal = listaMultaFragment()
            //trasaccion de fracmentos
            var transsaction = fragmentManager.beginTransaction()
            //reemplaza fragmento
            transsaction.replace(R.id.fragmentContainerView, fragmentPrincipal)

            transsaction.addToBackStack(null)
            //confirma los cambios
            transsaction.commit()
        }
        consultarMulta()
        cargarFormulario()

        return view


    }

    // vamos a crear un metodo para cargar el formula de todo
    fun cargarFormulario() {
        caragarEstadoMulta()
    }

    // generar las lista del spinner
    fun caragarEstadoMulta() {
        val estado_Multa = estado_Multa()

        // creamos un pequeño adapter para saber como se van a mostrar los datos
        val adapterSpinner = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            estado_Multa.listaEstadoMulta
        )
        SpinnerEstdo_multa.adapter = adapterSpinner
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