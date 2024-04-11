package com.example.medicsapplication.Fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.medicsapplication.Activities.LoginPage
import com.example.medicsapplication.Models.ProfileItems
import com.example.medicsapplication.PrefManager
import com.example.medicsapplication.R
import com.example.medicsapplication.databinding.FragmentProfileBinding
import com.example.medicsapplication.databinding.LayoutItemProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private lateinit var dbAuth:FirebaseAuth

    var profileItemsList = mutableListOf<ProfileItems>()
    lateinit var profileAdapter:ProfileListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbAuth = Firebase.auth
        profileAdapter = ProfileListAdapter(requireContext(),profileItemsList)
        binding.listView.adapter = profileAdapter
        prepareData()
        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            when(position){
                0 -> Toast.makeText(context, "mysaved clicked", Toast.LENGTH_SHORT).show()
                1 -> Toast.makeText(context, "appointment clicked", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(context, "payment method clicked", Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(context, "FAQS Clicked", Toast.LENGTH_SHORT).show()
                4 -> {

                    try {
                        dbAuth.signOut()
                        var manager = PrefManager(requireContext())
                        manager.updateLoginStatus(false)
                        showLogoutDialog()
                    }catch (e:Exception){

                    }
                }
            }
        }
    }

    private fun prepareData() {
        profileItemsList.add(ProfileItems(1,R.drawable.mysaved,"My saved"))
        profileItemsList.add(ProfileItems(2,R.drawable.appointment,"Appointment"))
        profileItemsList.add(ProfileItems(3,R.drawable.payment,"Payment Method"))
        profileItemsList.add(ProfileItems(4,R.drawable.faqs,"FAQs"))
        profileItemsList.add(ProfileItems(5,R.drawable.logout,"Logout"))
    }

    class ProfileListAdapter(
        var context: Context,
        var profileItemsList:MutableList<ProfileItems>
    ):BaseAdapter() {

        lateinit var bind: LayoutItemProfileBinding
        override fun getCount(): Int {
            return profileItemsList.size
        }

        override fun getItem(position: Int): Any {
            return profileItemsList[position]
        }

        override fun getItemId(position: Int): Long {
            return profileItemsList[position].id.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            bind = LayoutItemProfileBinding.inflate(LayoutInflater.from(context), parent, false)
            var profile = profileItemsList[position]

            bind.itemImg.setImageResource(profile.img)
            bind.itemTitle.text = profile.title

            return bind.root
        }
    }

    private fun showLogoutDialog() {
        var view = layoutInflater.inflate(R.layout.dialog_logout, null)
        var builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        var dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        var btnLogout = view.findViewById<Button>(R.id.btn_logout)
        var btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        btnLogout.setOnClickListener {
            var intent = Intent(requireContext(), LoginPage::class.java)
            startActivity(intent)
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}