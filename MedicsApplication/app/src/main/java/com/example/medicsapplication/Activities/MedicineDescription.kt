package com.example.medicsapplication.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicsapplication.Fragment.OrderFragment
import com.example.medicsapplication.Models.Order
import com.example.medicsapplication.Models.Product
import com.example.medicsapplication.databinding.ActivityMedicineDescriptionBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.squareup.picasso.Picasso

class MedicineDescription : AppCompatActivity() {
    lateinit var binding: ActivityMedicineDescriptionBinding
    private var quantity: Int = 1
    private var totalPrice: Int = 0
    private lateinit var product: Product
    private lateinit var dbAuth: FirebaseAuth
    private lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbAuth = Firebase.auth
        dbReference = Firebase.database.reference

        product = intent.getParcelableExtra("MEDICINE")!!
        product?.let {
            binding.productName.text = it.name
            binding.mfgName.text = "by ${it.mfg}"
            binding.ratingBar.rating = it.rating!!
            binding.productInfo.text = it.desc
            binding.proForm.text = it.form
            binding.productBenifit.text = it.benefits
            binding.productSafety.text = it.safety
            binding.finalPrice.text = "$ ${it.price.toString()}"
            Picasso.get().load(it.imgUrl).into(binding.thumbMedicine)
        }

        totalPrice = product.price!!

        updateQuantityAndPrice()

        binding.ivAdd.setOnClickListener {
            quantity++
            updateQuantityAndPrice()
        }
        binding.ivRemove.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantityAndPrice()
            }
        }
        binding.btnAddCart.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putInt("TOTALPRICE",totalPrice)
//            bundle.putInt("QUANTITY",quantity)
//            val orderFragment = OrderFragment()
//            orderFragment.arguments = bundle
//            finish()
            addToCart()
        }
    }

    private fun addToCart() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null){
            val userId = currentUser.uid
            val priceString = binding.finalPrice.text.toString()
            val price = priceString.replace("$ ","").toInt()
            val order = Order(userId,"",product.name!!,product.quantity!!,binding.tvQty.text.toString(),price)
            saveOrder(order)
            Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveOrder(order: Order) {
        val orderRef = dbReference.child("orders").child(dbAuth.currentUser!!.uid).child("orders")
        val orderId = orderRef.push().key

        orderId?.let {
            orderRef.child(it).setValue(order.copy(id = it))
        }
    }

    //
//
    private fun updateQuantityAndPrice() {
    //Update UI to display updated quantity

        binding.tvQty.text = quantity.toString()

    // Calculate price based on quantity
        totalPrice = quantity * product.price!!.toInt()
        binding.finalPrice.text = "$ ${totalPrice}"
    }
}