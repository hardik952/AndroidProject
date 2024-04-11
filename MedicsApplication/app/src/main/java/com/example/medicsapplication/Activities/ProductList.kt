package com.example.medicsapplication.Activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicsapplication.Adapter.ProductAdapter
import com.example.medicsapplication.Models.Product
import com.example.medicsapplication.databinding.ActivityListProductBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.storage.StorageReference

class ProductList : AppCompatActivity() {
    lateinit var binding: ActivityListProductBinding
    private var productList = mutableListOf<Product>()
    private lateinit var productAdapter: ProductAdapter
    lateinit var dbReference: DatabaseReference
    lateinit var dbAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbAuth = Firebase.auth
        dbReference = Firebase.database.reference

        productAdapter = ProductAdapter(this,productList)
        binding.listRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.listRecyclerView.adapter = productAdapter

        binding.btnAddMedicine.setOnClickListener {
            var intent = Intent(this,AddMedicineActivity::class.java)
            startActivity(intent)
        }
        dbReference.child("medicine-node").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (snap in snapshot.children){
                    var medicine = snap.getValue(Product::class.java)
                    productList.add(medicine!!)
                }
                productAdapter.setProductList(productList)
                productAdapter.itemDeleteClickListener = {position, product ->
                    dbReference.child("medicine-node").child(product.id!!).removeValue()
                    Toast.makeText(applicationContext, "Medicine Deleted", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "$error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}