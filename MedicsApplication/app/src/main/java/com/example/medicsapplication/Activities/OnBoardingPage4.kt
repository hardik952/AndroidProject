package com.example.medicsapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.medicsapplication.databinding.ActivityOnBordingPage4Binding

class OnBoardingPage4 : AppCompatActivity() {
    lateinit var binding: ActivityOnBordingPage4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBordingPage4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onbordingBtnLogin.setOnClickListener {
            var intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
        binding.onbordingBtnSignup.setOnClickListener {
            var intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }
}