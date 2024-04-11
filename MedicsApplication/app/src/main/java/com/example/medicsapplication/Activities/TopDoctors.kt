package com.example.medicsapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicsapplication.Adapter.TopDocAdapter
import com.example.medicsapplication.Models.Doctor
import com.example.medicsapplication.databinding.ActivityTopDoctorsBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class TopDoctors : AppCompatActivity() {
    lateinit var binding: ActivityTopDoctorsBinding
    private var docList = mutableListOf<Doctor>()
    private lateinit var doctorAdapter : TopDocAdapter
    private lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopDoctorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        prepareData()
        doctorAdapter = TopDocAdapter(this,docList)
        binding.topDoctorRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.topDoctorRecyclerview.adapter = doctorAdapter

        dbReference = Firebase.database.reference
        dbReference.child("doctor-node").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                docList.clear()

                for (snap in snapshot.children){
                    var doctor = snap.getValue(Doctor::class.java)
                    docList.add(doctor!!)
                }
                binding.topDoctorRecyclerview.setAdapter(doctorAdapter)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        binding.ivBackward5.setOnClickListener {
            var intent = Intent(this,HomePage::class.java)
            startActivity(intent)
        }
    }


//    private fun prepareData() {
//            docList.add(Doctor("1", R.drawable.doc1,"Dr. Marcus Horizon","Cardiologist", rating = 4.5f, distance = "800m away"))
//            docList.add(Doctor("2", R.drawable.doc2,"Dr. Maria Elena","Psychologist",rating = 4.9f,distance = "1.5km away"))
//            docList.add(Doctor("3", R.drawable.doc3,"Dr. Stefi Jessi","Orthopedist",rating = 4.7f,distance = "200m away"))
//            docList.add(Doctor("4", R.drawable.doc4,"Dr. Gerty Cori","Dentist",rating = 4.2f,distance = "800m away"))
//            docList.add(Doctor("5", R.drawable.doc5,"Dr. Jems Cruz","Gynecologist",rating = 4.8f,distance = "700m away"))
//    }
}