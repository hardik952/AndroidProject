package com.example.medicsapplication.Activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.medicsapplication.R
import com.example.medicsapplication.Models.Utils
import com.example.medicsapplication.databinding.ActivityCreateNewpasswordBinding

class CreateNewpassword : AppCompatActivity() {
    lateinit var binding: ActivityCreateNewpasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackward4.setOnClickListener {
            startActivity(Intent(this,ForgotPassword::class.java))
        }
        binding.btnNewpassword.setOnClickListener {
            var etNewpassword = binding.etNewpassword.text.toString().trim()
            var cnewpassword = binding.etCnewPassword.text.toString().trim()

            resetFocus()

            if (!Utils.isValidPassword(etNewpassword)) {
//                showError(binding.etNewpassword,"Enter valid password")
                binding.inputNewpassword.error = "Enter valid password"
            } else if (cnewpassword != etNewpassword) {
//                showError(binding.etCnewPassword,"Password mismatch")
                binding.inputCnewPassword.error = "Password mismatch"
            }else{
                showNewpasswordDialog()
            }
        }
    }

    private fun resetFocus() {
//        binding.etNewpassword.setBackgroundResource(R.drawable.edittext_white_bg)
        binding.inputNewpassword.isErrorEnabled = false
        binding.inputCnewPassword.isErrorEnabled = false
    }

    private fun showError(editText: EditText, error: String) {
        editText.setBackgroundResource(R.drawable.edittext_red_bg)
    }

    private fun showNewpasswordDialog() {
        var view = layoutInflater.inflate(R.layout.dialog_create_newpassword, null)
        var builder = AlertDialog.Builder(this)
        builder.setView(view)
        var dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        var btnloginnewpassword: Button = view.findViewById(R.id.btn_login_newpassword)
        btnloginnewpassword.setOnClickListener {
            var intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
    }
}