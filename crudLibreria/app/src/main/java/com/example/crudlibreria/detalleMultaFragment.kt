package com.example.crudlibreria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibreria.config.config
import com.example.crudlibreria.models.prestamo
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [detallePrestamoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class detalleMultaFragment : Fragment() {
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

    private lateinit var lblFecha_multa: TextView
    private lateinit var lblUsuario_multado: TextView
    private lateinit var lblPrestamo: TextView
    private lateinit var lblValor_multa: TextView
    private lateinit var Estado_multa: Spinner


    private lateinit var btnEditar: Button
    private lateinit var btnEliminar: Button


    // se asigna un id temporal existente
    private var id: Int = 0 // el entero no se puede quedar nulo

    fun consultarMulta() {
        // solo se debe consultar si el id es diferente a vacio
        if (id != 0) {
            var request = JsonObjectRequest(
                Request.Method.GET, //metodo
                config.urlMulta + id, //ur
                null,//datos de la peticion
                { response ->
                    //variable response contiene la respuesta de la api
                    //se convierte en json a un objeto tipo libro
                    //generamos un objeto de la libreria gson
                    val gson = Gson()
                    //se convierte
                    val usuario: prestamo = gson.fromJson(response.toString(), prestamo::class.java)
                    // se modifica el atributo text de los campos con el valor del objeto
                    lblFecha_multa.setText(response.getString("fecha_multa"))
                    lblUsuario_multado.setText(response.getString("usuario_multado"))
                    //Estado.setText(response.getString("Estado"))
                    lblPrestamo.setText(response.getString("prestamo"))
                    lblValor_multa.setText(response.getString("valor_multa"))


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detalle_multa, container, false)
        lblFecha_multa = view.findViewById(R.id.lblFecha_multa)
        lblUsuario_multado = view.findViewById(R.id.lblUsuario_multado)
        lblPrestamo = view.findViewById(R.id.lblPrestamo)
        lblValor_multa = view.findViewById(R.id.lblValor_multa)


        //se ejecuta la consulta
        consultarMulta()

        btnEditar = view.findViewById(R.id.btnGuardar)
        btnEditar.setOnClickListener { editarMulta() }
        btnEliminar = view.findViewById(R.id.btnEliminar)
        btnEliminar.setOnClickListener { eliminarMulta() }

        return view
    }

    //se crea el metodo ´para editarlinro
    fun editarMulta() {

    }

    fun eliminarMulta() {

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