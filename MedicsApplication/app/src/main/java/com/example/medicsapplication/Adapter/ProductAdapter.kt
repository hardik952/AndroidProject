package com.example.medicsapplication.Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.medicsapplication.Models.Order
import com.example.medicsapplication.Models.Product
import com.example.medicsapplication.databinding.LayoutAdapterProductBinding
import com.squareup.picasso.Picasso

class ProductAdapter(var context: Context, private var productList: MutableList<Product>):Adapter<ProductAdapter.MyViewHolder>() {
    var itemDeleteClickListener : ((position:Int,product: Product)-> Unit)? = null

    inner class MyViewHolder(var bind: LayoutAdapterProductBinding) : ViewHolder(bind.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutAdapterProductBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        var products = productList[position]
        holder.bind.medName.text = products.name
        holder.bind.medQty.text = products.quantity
        holder.bind.medPrice.text = "$ ${products.price.toString()}"
//        Picasso.get().load(products.imgUrl).into(holder.bind.ivThumbnailMed)
        holder.bind.ivDeleteMed.setOnClickListener {
            itemDeleteClickListener?.invoke(position,products)
        }
    }
    fun setProductList(mutableList: MutableList<Product>){
        this.productList = mutableList
        notifyDataSetChanged()
    }
}