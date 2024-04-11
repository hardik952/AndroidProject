package com.example.medicsapplication.Activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import com.example.medicsapplication.PrefManager
import com.example.medicsapplication.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility =View.SYSTEM_UI_FLAG_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
        Thread(Runnable {
              Thread.sleep(1000)
            var manager = PrefManager(this)

            if (manager.getLoginStatus()){
                var intent = Intent(this,HomePage::class.java)
                startActivity(intent)
                finish()
            }else {
                if (manager.getOnBoardingStatus()) {
                    var intent = Intent(this, LoginPage::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    var intent = Intent(this, OnBoardingScreen::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }).start()
    }
}