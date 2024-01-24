package com.example.basededatos.producto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.basededatos.ApplicationViewModel
import com.example.basededatos.R
import com.example.basededatos.databinding.FragmentCategoriaBinding
import com.example.basededatos.databinding.FragmentProductoBinding
import com.example.basededatos.entities.Categoria

// TODO: Rename parameter arguments, choose names that match



class ProductoFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val  viewmodel: ApplicationViewModel by activityViewModels<ApplicationViewModel>()
    private lateinit var binding: FragmentProductoBinding
    private lateinit var categoriaadapter:ArrayAdapter<Categoria>
    private var view:View?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_producto, container, false);

        context?.let {
            this.viewmodel.categorias.value?.let { it1 ->
                this.categoriaadapter = ArrayAdapter(
                    it, android.R.layout.simple_list_item_1,
                    it1.toTypedArray()
                )
                binding.categoriasListado.adapter = this.categoriaadapter
            }


        }
        binding.Cancelar.setOnClickListener {
            val fm: FragmentManager = parentFragmentManager
            fm.popBackStack()

        }
        binding.categoriasListado.onItemSelectedListener=this

        /*{ adapterView, view, pos, id ->
            run {
                var item=adapterView.getItemAtPosition(pos) as Categoria
                this.viewmodel.selectedproducto.value?.categoria=item;

            }
        }*/
        binding.aceptar.setOnClickListener{
            this.viewmodel.saveProducto()
            val toast = Toast.makeText(
                this.activity,
                "Producto almacenado", Toast.LENGTH_SHORT
            )
            toast.show()



        }
        binding.lifecycleOwner = this
        binding.delta = viewmodel
        view = binding.root;
        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ProductoFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var i=0;
        if (p0 != null) {
            this.viewmodel.selectedproducto.value?.categoria =(p0.getItemAtPosition(p2) as Categoria)

        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}