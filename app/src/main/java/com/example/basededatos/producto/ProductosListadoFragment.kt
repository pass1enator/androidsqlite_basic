package com.example.basededatos.producto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basededatos.ApplicationViewModel
import com.example.basededatos.R
import com.example.basededatos.categoria.CategoriaFragment
import com.example.basededatos.categoria.CategoriaRecyclerViewAdapter
import com.example.basededatos.entities.Categoria
import com.example.basededatos.entities.Producto
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 * Use the [ProductosListadoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductosListadoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val  viewmodel: ApplicationViewModel by activityViewModels<ApplicationViewModel>()
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

        view=inflater.inflate(R.layout.fragment_productos_listado, container, false)
        //listado
        view?.findViewById<RecyclerView>(R.id.listado)!!?.layoutManager = GridLayoutManager(context, 1)
        view?.findViewById<RecyclerView>(R.id.listado)!!.adapter = this.viewmodel.productos.value?.let {
            ProductoRecyclerViewAdapter(
                it
            )
        }
        //eventos internos del listado
        (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as ProductoRecyclerViewAdapter).editar_click =  { position:Int, item: Producto ->
            run {
               // this.viewmodel.setSelectedCategoria(item)
                val fm: FragmentManager = parentFragmentManager

                fm.commit {
                    replace(R.id.fragmentContainerView, CategoriaFragment.newInstance())
                    addToBackStack("editarcategoria")
                }

            }
        }
        (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as ProductoRecyclerViewAdapter).borrar_click =  { position:Int, item: Producto ->
            run {

                //viewmodel.deleteCategoria(item);
            }
        }
        //observar cuando cambian los elementos
        this.viewmodel.productos.observe(viewLifecycleOwner, {
            (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as ProductoRecyclerViewAdapter).setValues(it)
            (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as ProductoRecyclerViewAdapter).notifyDataSetChanged()

        })




        return view; //inflater.inflate(R.layout.fragment_categorias_listado, container, false)
        // Inflate the layout for this fragment

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductosListadoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ProductosListadoFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}