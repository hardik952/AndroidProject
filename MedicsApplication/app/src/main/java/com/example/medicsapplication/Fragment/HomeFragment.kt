package com.example.medicsapplication.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicsapplication.Activities.FindDoctor
import com.example.medicsapplication.Activities.Pharmacy
import com.example.medicsapplication.Activities.TopDoctors
import com.example.medicsapplication.Adapter.ArticleAdapter
import com.example.medicsapplication.Adapter.DoctorAdapter
import com.example.medicsapplication.Adapter.MedicineAdapter
import com.example.medicsapplication.Models.Article
import com.example.medicsapplication.Models.Category
import com.example.medicsapplication.Models.Doctor
import com.example.medicsapplication.Models.Product
import com.example.medicsapplication.R
import com.example.medicsapplication.databinding.FragmentHomeBinding
import com.example.medicsapplication.databinding.LayoutGridCategoryBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var docList = mutableListOf<Doctor>()
    private lateinit var docAdapter: DoctorAdapter
    private var medList = mutableListOf<Product>()
    private lateinit var medAdapter:MedicineAdapter
    private var articleList = mutableListOf<Article>()
    private lateinit var articleAdapter: ArticleAdapter
    private var categoryList = mutableListOf<Category>()
    private lateinit var categoryHomeAdapter:CategoryHomeAdapter
    private lateinit var dbReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        prepareData()

        docAdapter = DoctorAdapter(requireContext(),docList)
        binding.doctorsRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.doctorsRecyclerView.adapter = docAdapter

//        prepare medicine
        medAdapter = MedicineAdapter(requireContext(),medList)
        binding.mediRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mediRecyclerView.adapter = medAdapter

        prepareArticle()
        articleAdapter = ArticleAdapter(requireContext(),articleList)
        binding.articleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.articleRecyclerView.adapter = articleAdapter


        prepareCategory()
        categoryHomeAdapter = CategoryHomeAdapter(requireContext(),categoryList)
        binding.categoryGridView.adapter = categoryHomeAdapter
        binding.categoryGridView.setOnItemClickListener { parent, view, position, id ->
            when(position){
                0 -> startActivity(Intent(requireContext(),FindDoctor::class.java))
                1 -> startActivity(Intent(requireContext(),Pharmacy::class.java))
                2 -> Toast.makeText(requireContext(), "Hospital Clicked", Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(requireContext(), "Ambulance Clicked", Toast.LENGTH_SHORT).show()
            }
        }
        dbReference = Firebase.database.reference
        // Add doctor list
        dbReference.child("doctor-node").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                docList.clear()

                for (snap in snapshot.children){
                    var doctors = snap.getValue(Doctor::class.java)
                    docList.add(doctors!!)
                }
                binding.doctorsRecyclerView.setAdapter(docAdapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()
            }
        })
        // Add medicine list
        dbReference.child("medicine-node").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                medList.clear()

                for (snap in snapshot.children){
                    var medicines = snap.getValue(Product::class.java)
                    medList.add(medicines!!)
                }
                binding.mediRecyclerView.setAdapter(medAdapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()
            }
        })
        binding.btnSeeall1.setOnClickListener {
            var intent = Intent(requireContext(),TopDoctors::class.java)
            startActivity(intent)
        }
        binding.btnSeeall3.setOnClickListener {
            var intent  =Intent(requireContext(),Pharmacy::class.java)
            startActivity(intent)
        }
    }

    class CategoryHomeAdapter(var context: Context, private var categoryList: MutableList<Category>):BaseAdapter(){
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

    private fun prepareArticle() {
        articleList.add(Article(1,R.drawable.article1,"The 25 Healthiest Fruits You Can Eat, According to a Nutritionist","Jun 10,2021","5min read"))
        articleList.add(Article(2,R.drawable.article2,"Traditional Herbal Medicine Treatments for COVID-19","Jun 9,2021","8min read"))
        articleList.add(Article(3,R.drawable.article3,"Beauty Tips For Face: 10 Dos and Don't for Naturally Beautiful Skin","Jun 5,2021","10min read"))
        articleList.add(Article(4,R.drawable.article4,"Comparing the AstraZeneca and Sinovac COVID-19 Vaccines","Jun 1,2021","15min read"))
    }

//    private fun prepareData() {
//        docList.add(Doctor("1", R.drawable.doc1,"Dr. Marcus Cori","Cardiologist", rating = 4.5f, distance = "800m away"))
//        docList.add(Doctor("2", R.drawable.doc2,"Dr. Maria Elena","Psychologist",rating = 4.9f,distance = "1.5km away"))
//        docList.add(Doctor("3", R.drawable.doc3,"Dr. Stefi Jessi","Orthopedist",rating = 4.7f,distance = "200m away"))
//        docList.add(Doctor("4", R.drawable.doc4,"Dr. Gerty Cori","Dentist",rating = 4.2f,distance = "800m away"))
//        docList.add(Doctor("5", R.drawable.doc5,"Dr. Jems Cruz","Gynecologist",rating = 4.8f,distance = "700m away"))
//    }

    private fun prepareCategory() {
        categoryList.add(Category(1,R.drawable.icon_doctor,"General"))
        categoryList.add(Category(2,R.drawable.icon_pharmacy,"Pharmacy"))
        categoryList.add(Category(3,R.drawable.icon_hospital,"Hospital"))
        categoryList.add(Category(4,R.drawable.icon_ambulance,"Ambulance"))

    }
}