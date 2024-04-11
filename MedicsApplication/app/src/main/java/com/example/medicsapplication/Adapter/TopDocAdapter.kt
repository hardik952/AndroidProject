package com.example.medicsapplication.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.medicsapplication.Activities.DoctorDescription
import com.example.medicsapplication.Models.Doctor
import com.example.medicsapplication.databinding.LayoutTopDoctorAdapterBinding
import com.squareup.picasso.Picasso

class TopDocAdapter(var context: Context, private var docList: MutableList<Doctor>) :Adapter<TopDocAdapter.MyViewHolder>(){

    inner class MyViewHolder(var binding:LayoutTopDoctorAdapterBinding) : RecyclerView.ViewHolder(binding.root),
        android.view.View.OnClickListener{
        init {
            binding.topDocCard.setOnClickListener(this)
        }

        override fun onClick(view: android.view.View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                val doctor = docList[position]
                val intent = Intent(context, DoctorDescription::class.java)
                intent.putExtra("DOCTOR",doctor)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutTopDoctorAdapterBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return docList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var doc = docList[position]
        holder.binding.tvName2.text = doc.name
        holder.binding.tvType2.text = doc.speciality
        holder.binding.tvRating2.text = doc.rating?.toFloat().toString()
        holder.binding.tvDistance2.text = doc.distance
//        Picasso.get().load(doc.imgUrl).into(holder.binding.ivDoctor2)
    }
}