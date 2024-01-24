package com.example.basededatos.categoria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.example.basededatos.ApplicationViewModel
import com.example.basededatos.R
import com.example.basededatos.databinding.FragmentCategoriaBinding
import com.example.basededatos.entities.Producto
import com.example.basededatos.producto.ProductoFragment
import com.example.basededatos.producto.ProductosListadoFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CategoriaFragment : Fragment() {
    private val  viewmodel: ApplicationViewModel by activityViewModels<ApplicationViewModel>()
    private lateinit var binding: FragmentCategoriaBinding
    private var view:View?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_categoria,container,false);
        binding.lifecycleOwner = this
        binding.delta=viewmodel
        binding.Cancelar.setOnClickListener {
            val fm: FragmentManager = parentFragmentManager
            fm.popBackStack()

        }
        binding.aceptar.setOnClickListener{
            this.viewmodel.save()
            val toast = Toast.makeText(
                this.activity,
                "Categoría almacenada", Toast.LENGTH_SHORT
            )
            toast.show()



        }
        binding.addfloatingActionButton3.setOnClickListener {
            this.viewmodel.newProducto()
            val fm: FragmentManager = parentFragmentManager

            fm.commit {
                replace(R.id.fragmentContainerView, ProductoFragment.newInstance())
                addToBackStack("nuevaproducto")
            }

        }

        view=binding.root;
        //se obtiene el fragmentamanager del hijo
        val fm2: FragmentManager = childFragmentManager
        //se obtiene  el fragmento contenido y se le pasa la lambda para avisar
        var f=fm2.fragments.get(0) as ProductosListadoFragment
        f.editar_click= { position: Int, item: Producto ->
            run {
                val fm: FragmentManager = parentFragmentManager
                fm.commit {
                    replace(R.id.fragmentContainerView, ProductoFragment.newInstance())
                    addToBackStack("editarproducto")
                }

            }
        }

        return view //inflater.inflate(R.layout.fragment_categoria, container, false)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            CategoriaFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}