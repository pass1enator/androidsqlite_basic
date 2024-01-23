package com.example.basededatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basededatos.entities.Categoria
import com.example.basededatos.entities.Producto

class ApplicationViewModel(): ViewModel() {
    private lateinit var aplicationsqlliteopenhelper:AplicacionSQLiteOpenHelper
    private lateinit var db: SQLiteDatabase;
    private var new_item:Boolean=false
    private lateinit var _context:Context

    private var _categorias: MutableLiveData<MutableList<Categoria>> = MutableLiveData(mutableListOf())
    val categorias: LiveData<MutableList<Categoria>>
        get() = _categorias
    private var _selected =Categoria()
    var selected = MutableLiveData<Categoria>(Categoria())

    private var _productos: MutableLiveData<MutableList<Producto>> = MutableLiveData(mutableListOf())
    val productos: LiveData<MutableList<Producto>>
        get() = _productos
    private var _selectedproducto =Producto()
    var selectedproducto = MutableLiveData<Producto>(Producto())


    fun init(c:Context){
        this._context=c
        aplicationsqlliteopenhelper=AplicacionSQLiteOpenHelper(_context)
        db=this.aplicationsqlliteopenhelper.readableDatabase
        this.loadAllCategorias()
    }
    fun newCategoria(){
        //es nuevo, con id a -1
        this._selected=Categoria();
        this.selected.value=Categoria();
        //esta vacio
        this._productos.value= arrayListOf()
    }
    fun save(){
        //es para insertar
       if(_selected!=null && _selected!!.id==-1){
           _selected= selected.value!!
           _selected?.let { this.insertCategoria(it) }
       }else{
           //para actualizar
           _selected?.let {
               var item= selected.value
               if (item != null) {
                   it.activo=item.activo
                   it.descripcion=item.descripcion
                   it.nombre=item.nombre
                   this.updateCategoria(it);
                   //se avisa de los cambios
                   this.updatecategorias();
               }

           }
       }



    }
    fun setSelectedCategoria(c:Categoria){
        this._selected=c;
        //se hace una copia para evitar que se modifique
        this.selected.value=c.copy();
        //se cargan si es necesario
        if(this._selected.id!=-1)
            this._productos.value=Producto.loadByCategoria(this._selected,db);
    }
    fun insertCategoria(c:Categoria){

        c.insert(this.db);
        this.categorias.value?.add(c);
        this.updatecategorias()
    }
    fun updateCategoria(c:Categoria){
        c.update(this.db)
    }
    fun

            deleteCategoria(c:Categoria){
        c.delete(this.db)
        if(this._selected?.equals(c) == true){
            this._selected= Categoria()
        }
        this._categorias.value?.remove(c);
        this.updatecategorias()
    }
    fun loadAllCategorias(){
        var categorias=Categoria.loadAll(db);
        this.categorias
        this._categorias.value?.clear();
        categorias?.let { it1 ->
            it1.forEach {
                this._categorias.value?.add(it)
            }
        };
        this.updatecategorias()
    }
    private fun updatecategorias() {
        var values = this._categorias.value
        this._categorias.value = values
    }
    public fun  close(){
         this.db.close();
        this.aplicationsqlliteopenhelper.close();
    }


}