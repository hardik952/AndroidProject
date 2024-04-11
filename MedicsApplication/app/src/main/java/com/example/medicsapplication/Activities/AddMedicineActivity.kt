package com.example.medicsapplication.Activities

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.medicsapplication.Models.Product
import com.example.medicsapplication.databinding.ActivityMedicineAddBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage


class AddMedicineActivity : AppCompatActivity() {
    lateinit var binding: ActivityMedicineAddBinding
    private var imgUri: Uri? = null
    private lateinit var dbReference: DatabaseReference
    private lateinit var storageRef: StorageReference

    private var galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        /*  if (it.resultCode == RESULT_OK){
              var selectedImgURI = it.data?.data
              binding.medicineImg.setImageURI(selectedImgURI)
          }*/
        imgUri = it
        try {
            binding.medicineImg.setImageURI(imgUri)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbReference = Firebase.database.reference
        storageRef = Firebase.storage.reference

        binding.medicineImg.setOnClickListener {
            //   val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryLauncher.launch("image/*")
        }

        binding.btnSubmit.setOnClickListener {

            var name = binding.medicineName.text.toString().trim()
            var desc = binding.medicineDesc.text.toString().trim()
            var qty = binding.medicineQty.text.toString().trim()
            var mfg = binding.medicineMfg.text.toString().trim()
            var price = binding.medicineMrp.text.toString().trim().toInt()
            var discount = binding.medicineDiscount.text.toString().trim().toInt()
            var rating = binding.medicineRating.text.toString().trim().toFloat()
            var form = binding.medicineForm.text.toString().trim()
            var benefits = binding.medicineBenefit.text.toString().trim()

            getDownloadUri(imgUri) { imgUrl ->
                addMedicine(name, desc, qty, mfg, form, benefits, price, discount, rating, imgUrl!!)
            }
        }
    }

    private fun getDownloadUri(imgUri: Uri?, onComplete: (String?) -> Unit) {
        var dirName = "medicines"
        var fileName = "${System.currentTimeMillis()}.jpg"

        var uploadTask = storageRef.child(dirName).child(fileName).putFile(imgUri!!)

        uploadTask.continueWithTask {
            if (!it.isSuccessful) {
                it.exception?.let {
                    onComplete(null)
                }
            }
            // file successfully uploaded
            // getting download url
            storageRef.child(dirName).child(fileName).downloadUrl
        }.addOnCompleteListener {
            if (it.isSuccessful) {
                onComplete(it.result.toString())
            } else {
                onComplete(null)
            }
        }
    }

    private fun addMedicine(
        name: String,
        desc: String,
        qty: String,
        mfg: String,
        form: String,
        benefits: String,
        price: Int,
        discount: Int,
        rating: Float,
        imgUrl: String
    ) {

        dbReference.push().key?.let {
            var medicine = Product(
                id = it,
                name = name,
                desc = desc,
                quantity = qty,
                price = price,
                discount = discount,
                mfg = mfg,
                rating = rating,
                form = form,
                benefits = benefits,
                imgUrl = imgUrl
            )

            dbReference.child("medicine-node").child(it).setValue(medicine).addOnCompleteListener {

                if (it.isSuccessful) {
                    Toast.makeText(this, "Data added Successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}