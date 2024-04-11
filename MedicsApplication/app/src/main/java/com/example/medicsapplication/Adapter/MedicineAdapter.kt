package com.example.medicsapplication.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.medicsapplication.Activities.MedicineDescription
import com.example.medicsapplication.Models.Product
import com.example.medicsapplication.databinding.LayoutMedicineAdapterBinding
import com.squareup.picasso.Picasso

class MedicineAdapter(var context: Context, private var productList: MutableList<Product>):Adapter<MedicineAdapter.MyViewHolder>() {

    inner class MyViewHolder(var bind: LayoutMedicineAdapterBinding) : ViewHolder(bind.root),
        android.view.View.OnClickListener {
        init {
            bind.ivAddMedicine.setOnClickListener(this)
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val medicine = productList[position]
                val intent = Intent(context, MedicineDescription::class.java)
                intent.putExtra("MEDICINE", medicine)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutMedicineAdapterBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var medicine = productList[position]
        holder.bind.medicineName.text = medicine.name
        holder.bind.medineQuantity.text = medicine.quantity
        holder.bind.price.text = "$${medicine.price.toString()}"
//        Picasso.get().load(medicine.imgUrl).into(holder.bind.ivMedicine)
    }
}