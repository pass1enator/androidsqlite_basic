package com.example.basededatos.producto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.basededatos.ApplicationViewModel
import com.example.basededatos.R
import com.example.basededatos.databinding.FragmentCategoriaBinding
import com.example.basededatos.databinding.FragmentProductoBinding
import com.example.basededatos.entities.Categoria

// TODO: Rename parameter arguments, choose names that match


/**
 * A simple [Fragment] subclass.
 * Use the [ProductoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductoFragment : Fragment() {

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
       /* binding= DataBindingUtil.inflate(inflater, R.layout.fragment_producto,container,false);
        this.categoriaadapter=ArrayAdapter(, android.R.layout.simple_list_item_1, this.viewmodel.categorias.value)
        binding.lifecycleOwner = this
        binding.delta=viewmodel
        view=binding.root;
        return view*/


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
        //(it, android.R.layout.simple_list_item_1, this.viewmodel.categorias.value) }!!
        binding.lifecycleOwner = this
        binding.delta = viewmodel
        view = binding.root;
        return view
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_producto, container, false)
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
}