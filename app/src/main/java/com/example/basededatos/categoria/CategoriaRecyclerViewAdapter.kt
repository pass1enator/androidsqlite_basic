package com.example.basededatos.categoria

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basededatos.databinding.FragmentCategoriaitemBinding
import com.example.basededatos.entities.Categoria


class CategoriaRecyclerViewAdapter(
    private var values: MutableList<Categoria>


) : RecyclerView.Adapter<CategoriaRecyclerViewAdapter.ViewHolder>() {
    var editar_click: ((Int, Categoria) -> Unit)? = null
    var borrar_click: ((Int, Categoria) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCategoriaitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id.toString()//position.toString()
        holder.contentView.text = item.nombre
        holder.editarbutton.setOnClickListener {

            this.editar_click?.let { it1 -> it1(position, values[position]) }

        }
        holder.borrarbutton.setOnClickListener{
            this.borrar_click?.let { it1 -> it1(position, values[position]) }
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCategoriaitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        var editarbutton:Button= binding.editar
        var borrarbutton:Button= binding.borrar
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
    public fun setValues(v:MutableList<Categoria>){
        this.values=v;
        this.notifyDataSetChanged()
    }
}

