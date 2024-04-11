package com.example.medicsapplication.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicsapplication.Adapter.OrderAdapter
import com.example.medicsapplication.Models.Order
import com.example.medicsapplication.databinding.FragmentOrderBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class OrderFragment : Fragment() {
    lateinit var binding: FragmentOrderBinding
    lateinit var dbReference: DatabaseReference
    private lateinit var dbAuth: FirebaseAuth
    private var orderList = mutableListOf<Order>()
    private lateinit var orderAdapter: OrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        orderAdapter = OrderAdapter(requireContext(), orderList)
        binding.orderRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.orderRecyclerView.adapter = orderAdapter

        dbAuth = Firebase.auth
        dbReference = Firebase.database.reference

        dbReference.child("orders").child(dbAuth.currentUser!!.uid).child("orders")
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        orderList.clear()

                        for (snap in snapshot.children) {
                            var orders = snap.getValue(Order::class.java)
                            orderList.add(orders!!)
                        }
                        orderAdapter.setOrderList(orderList)
                        binding.tvBillPrice.text = orderAdapter.getBillPrice().toString()
                        orderAdapter.itemDeleteClickListener = { position, order ->
                            dbReference.child("orders").child(dbAuth.currentUser!!.uid).child("orders").child(order.id!!).removeValue()
                            Toast.makeText(requireContext(), "Order Item Deleted", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()
                    }
                })
        binding.btnPlaceOrder.setOnClickListener {
            Toast.makeText(requireContext(), "Your order is Confirmed", Toast.LENGTH_SHORT).show()
        }
    }
}