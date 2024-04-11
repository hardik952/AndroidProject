package com.example.medicsapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.medicsapplication.Models.Items
import com.example.medicsapplication.databinding.LayoutOnbordingItemBinding

class ViewPagerAdapter(var context: Context, var itemList: MutableList<Items>) :
    PagerAdapter() {

    lateinit var binding: LayoutOnbordingItemBinding

    override fun getCount(): Int {
        return itemList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var items = itemList[position]
        binding = LayoutOnbordingItemBinding.inflate(LayoutInflater.from(context), container, false)
        binding.docThumbnail.setImageResource(items.docPic)
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }

    fun getItem(position: Int): Items {
        return itemList[position]
    }
}