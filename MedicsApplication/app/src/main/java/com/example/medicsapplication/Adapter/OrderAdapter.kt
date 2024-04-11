package com.example.medicsapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.medicsapplication.Models.Order
import com.example.medicsapplication.databinding.LayoutOrderAdapterBinding
import com.squareup.picasso.Picasso

class OrderAdapter(var context: Context, private var orderList: MutableList<Order>) :Adapter<OrderAdapter.MyViewHolder>(){
    inner class MyViewHolder(var bind: LayoutOrderAdapterBinding) : ViewHolder(bind.root)

    var itemDeleteClickListener : ((position:Int,order:Order)-> Unit)? = null
    var itemEditClickListener : ((position:Int,order:Order)-> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutOrderAdapterBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var orders = orderList[position]
        holder.bind.proName.text = orders.medName
        holder.bind.proQty.text = orders.medQty
        holder.bind.orderQty.text = orders.orderQty
        holder.bind.proPrice.text = "$ ${orders.price.toString()}"
//        Picasso.get().load(orders.imgUrl).into(holder.bind.thumbnailPro)
        holder.bind.ivDelete.setOnClickListener {
            itemDeleteClickListener?.invoke(position,orders)
        }

    }
    fun setOrderList(mutableList: MutableList<Order>){
        this.orderList = mutableList
        notifyDataSetChanged()
    }

    fun getBillPrice():Double{
        var billPrice = 0.0
        for (order in orderList){
            billPrice += order.price!!
        }
        return billPrice
    }
}