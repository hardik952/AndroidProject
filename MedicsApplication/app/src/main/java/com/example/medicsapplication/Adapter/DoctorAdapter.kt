package com.example.medicsapplication.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.medicsapplication.Activities.DoctorDescription
import com.example.medicsapplication.Models.Doctor
import com.example.medicsapplication.databinding.LayoutDocAdapterBinding
import com.squareup.picasso.Picasso


class DoctorAdapter(var context:Context, var docList: MutableList<Doctor>): Adapter<DoctorAdapter.MyViewHolder>(){

    inner class MyViewHolder(var binding:LayoutDocAdapterBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutDocAdapterBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return docList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var doctors = docList[position]
        holder.binding.tvName.text = doctors.name
        holder.binding.tvType.text = doctors.speciality
        holder.binding.tvRating.text = doctors.rating?.toFloat().toString()
        holder.binding.tvDistance.text = doctors.distance
//        Picasso.get().load(doctors.imgUrl).into(holder.binding.ivDoc)
    }
}