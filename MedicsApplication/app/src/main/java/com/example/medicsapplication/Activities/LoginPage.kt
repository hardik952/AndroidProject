package com.example.medicsapplication.Activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.medicsapplication.Models.Utils
import com.example.medicsapplication.R
import com.example.medicsapplication.PrefManager
import com.example.medicsapplication.databinding.ActivityLoginPageBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginPage : AppCompatActivity() {
    lateinit var binding: ActivityLoginPageBinding
    private lateinit var dbAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbAuth = Firebase.auth

        binding.btnSignupLoginpage.setOnClickListener {
            var intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
        binding.ivBackward1.setOnClickListener {
            var intent = Intent(this, OnBoardingPage4::class.java)
            startActivity(intent)
        }
        binding.btnForgetpassword.setOnClickListener {
            var intent =  Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }
        binding.btnLoginLoginpage.setOnClickListener {
            var email = binding.etEmailLoginpage.text.toString().trim()
            var password = binding.etPasswordLoginpage.text.toString().trim()



            resetFocus()

            if (!Utils.isValidEmail(email)){
//                customised validation
//                showError(binding.etEmailLoginpage,binding.etEmailLoginpageLayout,"Enter valid Email")
                binding.etEmailLoginpageLayout.error = "Enter valid email"  //validation
            }else if (!Utils.isValidPassword(password)){
//                showError(binding.etPasswordLoginpage,binding.etPasswordLayout,"Enter valid Password")
                binding.etPasswordLayout.error = "Enter valid password"
            }else{
//                showLoginDialog()
            loginAccount(email,password)
            }
        }
    }

    private fun loginAccount(email: String, password: String) {
        dbAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {

            if (it.isSuccessful){
                //navigate to home
                showLoginDialog()
            }else{
                //Error
            }
        }

    }

    private fun resetFocus() {
//        textInputLayout.isHelperTextEnabled = false
//        editText.setBackgroundResource(R.drawable.bg_edittext)
        binding.etPasswordLayout.isErrorEnabled = false
        binding.etEmailLoginpageLayout.isErrorEnabled = false
//        binding.etEmailLoginpage.setBackgroundResource(R.drawable.bg_edittext)
//        binding.etPasswordLoginpage.setBackgroundResource(R.drawable.bg_edittext)
    }
    private fun showError(textInputEditText: TextInputEditText,textInputLayout: TextInputLayout ,error: String){
        textInputEditText.setBackgroundResource(R.drawable.edittext_red_bg)
        textInputEditText.requestFocus()
        Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
    }

    private fun showLoginDialog() {
        var view = layoutInflater.inflate(R.layout.dialog_login,null)
        var builder = AlertDialog.Builder(this)
        builder.setView(view)
        var dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        var btnHome : Button = view.findViewById(R.id.btn_login_home)
        btnHome.setOnClickListener {
//            Toast.makeText(this,"Home button clicked",Toast.LENGTH_SHORT).show()
            var manager = PrefManager(this)
            manager.updateLoginStatus(true)
            var intent = Intent(this,HomePage::class.java)
            startActivity(intent)
            dialog.dismiss()
            finishAffinity()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        var currentUser = mAuth.currentUser
//        if (currentUser != null) {
//            // already sign in
//          startActivity(Intent(this,HomePage::class.java))
////            Toast.makeText(this, "user already login", Toast.LENGTH_SHORT).show()
//        }
//    }
    override fun onStart() {
        super.onStart()
        var currentUser = dbAuth.currentUser
        if (currentUser != null) {

        //  already sign in
            startActivity(Intent(this, HomePage::class.java))
            Toast.makeText(this, "user already login", Toast.LENGTH_SHORT).show()
        }
    }
}