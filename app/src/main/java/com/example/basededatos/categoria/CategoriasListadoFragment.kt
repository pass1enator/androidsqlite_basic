package com.example.basededatos.categoria

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
import com.example.basededatos.entities.Categoria
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 * Use the [CategoriasListadoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriasListadoFragment : Fragment() {
    private val  viewmodel: ApplicationViewModel by activityViewModels<ApplicationViewModel>()
    private var view:View?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        view=inflater.inflate(R.layout.fragment_categorias_listado, container, false)
        //listado
        view?.findViewById<RecyclerView>(R.id.listado)!!?.layoutManager = GridLayoutManager(context, 1)
        view?.findViewById<RecyclerView>(R.id.listado)!!.adapter = this.viewmodel.categorias.value?.let {
            CategoriaRecyclerViewAdapter(
                it
            )
        }
        //eventos internos del listado
        (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as CategoriaRecyclerViewAdapter).editar_click =  { position:Int, c: Categoria ->
            run {
                this.viewmodel.setSelectedCategoria(c)
                val fm: FragmentManager = parentFragmentManager

                fm.commit {
                    replace(R.id.fragmentContainerView, CategoriaFragment.newInstance())
                    addToBackStack("editarcategoria")
                }

            }
        }
        (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as CategoriaRecyclerViewAdapter).borrar_click =  { position:Int, c: Categoria ->
            run {

                viewmodel.deleteCategoria(c);
            }
        }
        //observar cuando cambian los elementos
        this.viewmodel.categorias.observe(viewLifecycleOwner, {
            (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as CategoriaRecyclerViewAdapter).setValues(it)
            (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as CategoriaRecyclerViewAdapter).notifyDataSetChanged()

        })
        view?.findViewById<FloatingActionButton>(R.id.addfloatingActionButton)?.setOnClickListener {
            this.viewmodel.newCategoria()
            val fm: FragmentManager = parentFragmentManager

            fm.commit {
                replace(R.id.fragmentContainerView, CategoriaFragment.newInstance())
                addToBackStack("nuevacategoria")
            }

        }



        return view; //inflater.inflate(R.layout.fragment_categorias_listado, container, false)
    }

    companion object {


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoriasListadoFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}