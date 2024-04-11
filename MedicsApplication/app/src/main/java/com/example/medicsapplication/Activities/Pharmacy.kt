package com.example.medicsapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicsapplication.Adapter.MedicineAdapter
import com.example.medicsapplication.Adapter.MedicineSaleAdapter
import com.example.medicsapplication.Models.Product
import com.example.medicsapplication.R
import com.example.medicsapplication.databinding.ActivityPharmacyBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class Pharmacy : AppCompatActivity() {
    lateinit var binding: ActivityPharmacyBinding
    private var productList = mutableListOf<Product>()
    private lateinit var medicineAdapter: MedicineAdapter
    private var saleList = mutableListOf<Product>()
    private lateinit var saleAdapter: MedicineSaleAdapter
    private lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPharmacyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackward7.setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
        }
//        prepareMedicine()
        medicineAdapter = MedicineAdapter(this, productList)
        binding.medicineRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.medicineRecyclerView.adapter = medicineAdapter

//        prepareSale()
        saleAdapter = MedicineSaleAdapter(this, saleList)
        binding.saleRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.saleRecyclerView.adapter = saleAdapter

        dbReference = Firebase.database.reference
        dbReference.child("medicine-node").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (snap in snapshot.children) {
                    var medicine = snap.getValue(Product::class.java)
                    productList.add(medicine!!)

                    var saleMedicine = snap.getValue(Product::class.java)
                    saleList.add(saleMedicine!!)
                }
                binding.medicineRecyclerView.setAdapter(medicineAdapter)
                binding.saleRecyclerView.setAdapter(saleAdapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "$error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    /*private fun prepareMedicine() {
        productList.add(
            Product(
                "1",
                "Penadol",
                "Calciquick D3 60K Capsule is a dietary supplement that is crucial in numerous body process",
                "20pcs",
                15,
                0,
                "Morepen Laboratories Ltd",
                4.5f,
                "Capsule",
                "Promotes bone health\nBoosts immunity\nImproves muscle function",
                "Read the lable carefully before use"
            )
        )
        productList.add(
            Product(
                "2",
                R.drawable.medicine1,
                "Penadol",
                "Calciquick D3 60K Capsule is a dietary supplement that is crucial in numerous body process",
                "20pcs",
                15,
                0,
                "Morepen Laboratories Ltd",
                4.5f,
                "Capsule",
                "Promotes bone health\nBoosts immunity\nImproves muscle function",
                "Read the lable carefully before use"
            )
        )
        productList.add(
            Product(
                "2",
                R.drawable.medicine1,
                "Penadol",
                "Calciquick D3 60K Capsule is a dietary supplement that is crucial in numerous body process",
                "20pcs",
                15,
                0,
                "Morepen Laboratories Ltd",
                4.5f,
                "Capsule",
                "Promotes bone health\nBoosts immunity\nImproves muscle function",
                "Read the lable carefully before use"
            )
        )
        productList.add(
            Product(
                "4",
                R.drawable.medicine1,
                "Penadol",
                "Calciquick D3 60K Capsule is a dietary supplement that is crucial in numerous body process",
                "20pcs",
                15,
                0,
                "Morepen Laboratories Ltd",
                4.5f,
                "Capsule",
                "Promotes bone health\nBoosts immunity\nImproves muscle function",
                "Read the lable carefully before use"
            )
        )

    }

    private fun prepareSale() {
        saleList.add(
            Product(
                "1",
                R.drawable.medicine1,
                "Betadine",
                quantity = "20pcs",
                discount = 5,
                price = 90
            )
        )
        saleList.add(
            Product(
                "2",
                R.drawable.medicine2,
                "Betadine",
                quantity = "100ml",
                discount = 15,
                price = 60

            )
        )
        saleList.add(
            Product(
                "3",
                R.drawable.medicine3,
                "Betadine",
                quantity = "20pcs",
                discount = 10,
                price = 70
            )
        )
        saleList.add(
            Product(
                "4",
                R.drawable.medicine4,
                "Betadine",
                quantity = "20pcs",
                discount = 7,
                price = 150
            )
        )
    }*/
}