package com.example.medicsapplication.Activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.medicsapplication.Fragment.AppointmentFragment
import com.example.medicsapplication.Fragment.HomeFragment
import com.example.medicsapplication.Fragment.OrderFragment
import com.example.medicsapplication.Fragment.ProfileFragment
import com.example.medicsapplication.PrefManager
import com.example.medicsapplication.R
import com.example.medicsapplication.databinding.ActivityHomePageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomePage : AppCompatActivity() {
    lateinit var binding: ActivityHomePageBinding
    private lateinit var dbAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        dbAuth = Firebase.auth
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.drawerNavMenu.setNavigationItemSelectedListener {
            it.isCheckable = true
            binding.drawerLayout.close()

            when(it.itemId){
                R.id.nav_home -> {
                    addFragment(HomeFragment())
                    true
                }
                R.id.nav_add_product -> {
                    var intent = Intent(this,AddMedicineActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_manage_product -> {
                    var intent = Intent(this,ProductList::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_add_doctor ->{
                    var intent = Intent(this,AddDoctorActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_terms -> {
                    true
                }
                R.id.nav_logout -> {
                    dbAuth.signOut()
                    var manager = PrefManager(this)
                    manager.updateLoginStatus(false)
                    showLogoutDialog()
                    true
                }
                else -> false
            }
        }

        addFragment(HomeFragment())

        binding.bottomNavi.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    addFragment(HomeFragment())
                    true
                }
//                R.id.message -> {
//                    addFragment(MessageFragment())
//                    true
//                }
                R.id.appointment -> {
                    addFragment(AppointmentFragment())
                    true
                }
                R.id.bag -> {
                    addFragment(OrderFragment())
                    true
                }
                R.id.profile -> {
                    addFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        var manager = supportFragmentManager
        var transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()

    }
    private fun showLogoutDialog() {
        var view = layoutInflater.inflate(R.layout.dialog_logout, null)
        var builder = AlertDialog.Builder(this)
        builder.setView(view)
        var dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        var btnLogout = view.findViewById<Button>(R.id.btn_logout)
        var btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        btnLogout.setOnClickListener {
            var intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}