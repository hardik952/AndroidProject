package com.example.medicsapplication.Activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.medicsapplication.Models.User
import com.example.medicsapplication.R
import com.example.medicsapplication.Models.Utils
import com.example.medicsapplication.databinding.ActivityRegisterPageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import java.util.Calendar

class RegisterPage : AppCompatActivity() {
    lateinit var binding: ActivityRegisterPageBinding
    private lateinit var dbReference: DatabaseReference
    private lateinit var dbAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbAuth = Firebase.auth
        dbReference = Firebase.database.reference     // Contains database root reference
        binding.ivCalender.setOnClickListener {
            showDatePickerDialog()
        }
        binding.ivBackward2.setOnClickListener {
            startActivity(Intent(this,OnBoardingPage4::class.java))
        }

        binding.btnSignupSignuppage.setOnClickListener {
            var name = binding.etName.text.toString().trim()
            var email = binding.etEmailSignup.text.toString().trim()
            var birthdate = binding.etBirthdate.text.toString().trim()
            var phone = binding.etPhone.text.toString().trim()
            var password = binding.etPasswordSignup.text.toString().trim()
            var conPassword = binding.etCpassword.text.toString().trim()
            var checkBox = binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    binding.checkbox.error = "Please accept terms and conditions"
                }else{
                    Toast.makeText(this, "Terms and Condition is accepted", Toast.LENGTH_SHORT).show()
                    binding.checkbox.error = null
                }
            }

            resetFocus()

            if (name.isNullOrEmpty()) {
//            showError(binding.etName,"Enter valid name")
                binding.etNameLayout.error = "Enter valid name"
            } else if (!Utils.isValidEmail(email)) {
//            showError(binding.etEmailSignup,"Enter valid email")
                binding.etEmailLayoutSignup.error = "Enter valid email"
            } else if (birthdate.isEmpty()) {
//            showError(binding.etBirthdate,"Enter valid birthdate")
                binding.etBirthdateLayout.error = "Enter valid birthdate"
            } else if (!Utils.isValidContact(phone)) {
//            showError(binding.etEmailSignup,"Enter valid number")
                binding.etPhoneLayout.error = "Enter valid phone"
            } else if (!Utils.isValidPassword(password)) {
//                showError(binding.etPasswordSignup,"Enter valid password")
                binding.etPasswordLayoutSignup.error = "Enter valid password"
            } else if (conPassword != password) {
//            showError(binding.etCpassword,"Password mismatch")
                binding.etCpasswordLayout.error = "Password mismatch"
            } else {
                createAccount(name,email,birthdate,phone,password)
            }
        }
    }

    private fun createAccount(name: String, email: String, birthdate: String,phone: String, password: String) {
        dbAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {

            if (it.isSuccessful){

                // get uid and email
                var userCredential = it.result.user
                var user = User(userCredential!!.uid,name,userCredential.email!!,phone,birthdate)

                /*Toast.makeText(this, """
                    uid : ${user!!.uid}
                    email : ${user!!    .email}
                    name : ${name}
                """.trimIndent(), Toast.LENGTH_LONG).show()*/

                dbReference.child("user-node").child(user.id).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful){
                        showRegisterDialog()
                    }
                }
            }
            else{
                //error
                Toast.makeText(this, "${it.exception!!.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resetFocus() {
//        binding.etName.setBackgroundResource(R.drawable.edittext_white_bg)
//        binding.etEmailSignup.setBackgroundResource(R.drawable.edittext_white_bg)
//        binding.etBirthdate.setBackgroundResource(R.drawable.edittext_white_bg)
//        binding.etPhone.setBackgroundResource(R.drawable.edittext_white_bg)
//        binding.etPasswordSignup.setBackgroundResource(R.drawable.edittext_white_bg)
//        binding.etCpassword.setBackgroundResource(R.drawable.edittext_white_bg)
        binding.etNameLayout.isErrorEnabled = false
        binding.etEmailLayoutSignup.isErrorEnabled = false
        binding.etBirthdateLayout.isErrorEnabled = false
        binding.etPhoneLayout.isErrorEnabled = false
        binding.etPasswordLayoutSignup.isErrorEnabled = false
        binding.etCpasswordLayout.isErrorEnabled = false
    }

    private fun showError(editText: EditText,error: String) {
        editText.setBackgroundResource(R.drawable.edittext_red_bg)
        editText.requestFocus()
        Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
    }

    private fun showRegisterDialog() {
        var view = layoutInflater.inflate(R.layout.dialog_signup, null)
        var builder = AlertDialog.Builder(this)
        builder.setView(view)
        var dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        var btnLogin = view.findViewById<Button>(R.id.btn_dialog_login)
        btnLogin.setOnClickListener {
            var intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            dialog.dismiss()
            finishAffinity()
        }
    }

    private fun showDatePickerDialog() {
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        var datePicker = DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
//                val selectedDate = Calendar.getInstance()
//                selectedDate.set(year,month,dayOfMonth)
//                var dateFormat = SimpleDateFormat("dd/mm/yyyy",Locale.getDefault())
                var date = "${dayOfMonth.toString().padStart(2, '0')}/${
                    (month + 1).toString().padStart(2, '0')
                }/$year"
//                var formattedDate = dateFormat.format(selectedDate.time)
//                binding.etBirthdate.text = "Selected Date: $formattedDate"
                binding.etBirthdate.setText(date)
            }, year, month, dayOfMonth
        )
        datePicker.show()
    }
}