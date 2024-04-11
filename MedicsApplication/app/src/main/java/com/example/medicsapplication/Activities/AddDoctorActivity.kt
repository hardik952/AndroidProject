package com.example.medicsapplication.Activities

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.medicsapplication.Models.Doctor
import com.example.medicsapplication.databinding.ActivityAddDoctorBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage

class AddDoctorActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddDoctorBinding
    private lateinit var dbReference: DatabaseReference
    private lateinit var storageRef: StorageReference
    private var imgUri: Uri? = null

    private var galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imgUri = it
        try {
            binding.imgDoc.setImageURI(imgUri)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbReference = Firebase.database.reference
        storageRef = Firebase.storage.reference

        binding.imgDoc.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.submitBtn.setOnClickListener {
            var name = binding.nameDoc.text.toString().trim()
            var speciality = binding.specialityDoc.text.toString().trim()
            var intro = binding.introDoc.text.toString().trim()
            var place = binding.workplaceDoc.text.toString().trim()
            var phone = binding.phoneDoc.text.toString().trim()
            var address = binding.addressDoc.text.toString().trim()
            var timing = binding.timeDoc.text.toString().trim()
            var days = binding.daysDoc.text.toString().trim()
            var fees = binding.feesDoc.text.toString().trim().toInt()
            var rating = binding.ratingDoc.text.toString().trim().toFloat()

            getDownloadUrl(imgUri) { imgUrl ->
                addDoctor(name,speciality,intro,place,phone,address,timing,rating,days,fees,imgUrl!!)
            }
       }
}

    private fun getDownloadUrl(imgUri: Uri?, onComplete:(String?)-> Unit) {
        var dirName = "Doctors"
        var fileName = "${System.currentTimeMillis()}.jpg"

        var uploadTask = storageRef.child(dirName).child(fileName).putFile(imgUri!!)
        uploadTask.continueWithTask {
            if (!it.isSuccessful){
                it.exception?.let {
                    onComplete(null)
                }
            }
            // file successfully uploaded
            // getting download uri
            storageRef.child(dirName).child(fileName).downloadUrl
        }.addOnCompleteListener {
            if (it.isSuccessful){
                onComplete(it.result.toString())
            }else{
                onComplete(null)
            }
        }
    }

    private fun addDoctor(name: String, speciality: String, intro: String, place: String, phone: String, address: String, timing: String,rating: Float,days:String,fees:Int,imgUrl: String
    ) {
        dbReference.push().key?.let {
            var doc = Doctor(it, name = name, speciality = speciality, intro = intro, place = place, phone =  phone, address = address, timing = timing, rating = rating, days = days, fees =fees, imgUrl = imgUrl)
            dbReference.child("doctor-node").child(it).setValue(doc).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "doctor data add successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
