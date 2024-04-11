package com.example.medicsapplication.Activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.medicsapplication.R
import com.example.medicsapplication.Models.Utils
import com.example.medicsapplication.databinding.ActivityForgotPasswordBinding

class ForgotPassword : AppCompatActivity() {
    lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackward3.setOnClickListener {
            startActivity(Intent(this,LoginPage::class.java))
        }
        
        binding.btnResetpassword.setOnClickListener {
            var resetemail = binding.etEmailForgot.text.toString().trim()

            resetFocus()

            if (!Utils.isValidEmail(resetemail)){
//                showError(binding.etEmailForgot,"Enter valid email")
                binding.etEmailForgotLayout.error = "Enter valid email"
            }
        }
        binding.btnPhone.setOnClickListener {
            binding.etEmailForgotLayout.hint = "Enter your phone number"
            binding.btnPhone.setTextColor(Color.CYAN)
            binding.btnEmail.setTextColor(Color.LTGRAY)
        }
        binding.btnEmail.setOnClickListener {
            binding.etEmailForgotLayout.hint = "Enter your email address"
            binding.btnEmail.setTextColor(Color.CYAN)
            binding.btnPhone.setTextColor(Color.LTGRAY)
        }
    }

    private fun resetFocus() {
//        binding.etEmailForgot.setBackgroundResource(R.drawable.edittext_white_bg)
        binding.etEmailForgotLayout.isErrorEnabled = false
        }
    private fun showError(editText: EditText,error: String){
        editText.setBackgroundResource(R.drawable.edittext_red_bg)
        editText.requestFocus()
    }
}