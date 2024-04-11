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
import com.example.medicsapplication.databinding.LayoutSaleMedicineBinding
import com.squareup.picasso.Picasso

class MedicineSaleAdapter(var context: Context, private var saleList: MutableList<Product>):Adapter<MedicineSaleAdapter.MyViewHolder>() {
   inner class MyViewHolder(var bind:LayoutSaleMedicineBinding) : ViewHolder(bind.root),
       android.view.View.OnClickListener {
           init {
               bind.addMedicine.setOnClickListener(this)
           }
       override fun onClick(view: android.view.View) {
           var position = adapterPosition
           if (position == RecyclerView.NO_POSITION){
               val medicine = saleList[position]
               val intent = Intent(context,MedicineDescription::class.java)
               intent.putExtra("MEDICINE",medicine)
               context.startActivity(intent)
           }
       }

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutSaleMedicineBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return saleList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var medicine = saleList[position]
        holder.bind.nameMedicine.text = medicine.name
        holder.bind.medineQty.text = medicine.quantity
        holder.bind.salePrice.text = "$${medicine.price.toString()}"
        holder.bind.saleDisPrice.text = "$${medicine.disPrice.toString()}"
        holder.bind.tvPercentage.text = "${medicine.discount}% OFF"
//        Picasso.get().load(medicine.imgUrl).into(holder.bind.thumbnailMedicine)
    }
}