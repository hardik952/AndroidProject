package com.example.medicsapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.medicsapplication.Models.Items
import com.example.medicsapplication.R
import com.example.medicsapplication.R.drawable
import com.example.medicsapplication.Adapter.ViewPagerAdapter
import com.example.medicsapplication.PrefManager
import com.example.medicsapplication.databinding.ActivityOnBordingScreenBinding


class OnBoardingScreen : AppCompatActivity() {
    private lateinit var binding: ActivityOnBordingScreenBinding
    private var itemList = mutableListOf<Items>()
    private lateinit var pagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBordingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prerparData()

        pagerAdapter = ViewPagerAdapter(this,itemList)
        binding.viewPager.adapter = pagerAdapter

        updateIndicator(0)

        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.tvText.text = pagerAdapter.getItem(position).text
                updateIndicator(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

                }

        })

    }

    private fun updateIndicator(position: Int) {

        binding.layoutDots.removeAllViews()

        for (i in 0 until pagerAdapter.count){

                var imageView = ImageView(this)

            if (position==i){
                imageView.setBackgroundResource(R.drawable.active_indicator)
            }else{
                imageView.setBackgroundResource(R.drawable.inactive_indicator)
            }
            var params =
                LinearLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT)
            params.setMargins(8, 0, 8, 0)
            binding.layoutDots.addView(imageView, params)

            var textView = TextView(this)
        }
    }

    private fun prerparData() {
        itemList.add(Items(1, drawable.onboard1,"Consult only with the doctor you trust"))
        itemList.add(Items(2, drawable.onboard2,"Find a lot of specialist doctors in one place"))
        itemList.add(Items(3, drawable.onboard3,"Get connect our Online Consultation"))
    }
    fun onButtonClick(view: View){
        var manager = PrefManager(this)
        manager.updateOnBoardingStatus(true)
        var intent = startActivity(Intent(this,OnBoardingPage4::class.java))
        finish()
    }
}
