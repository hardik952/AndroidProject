package com.example.medicsapplication.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.medicsapplication.Models.Category
import com.example.medicsapplication.R
import com.example.medicsapplication.databinding.ActivityFindDoctorBinding
import com.example.medicsapplication.databinding.LayoutGridCategoryBinding

class FindDoctor : AppCompatActivity() {
    lateinit var binding: ActivityFindDoctorBinding
    var categoryList = mutableListOf<Category>()
    lateinit var catgoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackward6.setOnClickListener {
            startActivity(Intent(this,HomePage::class.java))
        }

        prepareCategory()
        catgoryAdapter = CategoryAdapter(this,categoryList)
        binding.gridView.adapter = catgoryAdapter
    }

    private fun prepareCategory() {
        categoryList.add(Category(1,R.drawable.icon_general,"General"))
        categoryList.add(Category(2,R.drawable.icon_lungs,"Lung Specialist"))
        categoryList.add(Category(3,R.drawable.icon_dentist,"Dentist"))
        categoryList.add(Category(4,R.drawable.icon_psychiatrist,"Psychiatrist"))
        categoryList.add(Category(5,R.drawable.icon_covid,"Covid-19"))
        categoryList.add(Category(6,R.drawable.icon_surgeon,"Surgeon"))
        categoryList.add(Category(7,R.drawable.icon_cardiologist,"Cardiologist"))
    }

    class CategoryAdapter(var context: Context, var categoryList: MutableList<Category>) :
        BaseAdapter() {
            lateinit var bind: LayoutGridCategoryBinding
        override fun getCount(): Int {
            return categoryList.size
        }

        override fun getItem(position: Int): Any {
            return categoryList[position]
        }

        override fun getItemId(position: Int): Long {
            return categoryList[position].id.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            bind = LayoutGridCategoryBinding.inflate(LayoutInflater.from(context),parent,false)
            var category = categoryList[position]
            bind.ivCategory.setImageResource(category.categoryImg)
            bind.tvCategory.text = category.title
            return bind.root
        }

    }
}

