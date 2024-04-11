package com.example.medicsapplication.Activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicsapplication.Models.Doctor
import com.example.medicsapplication.databinding.ActivityDoctorDescriptionBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import java.util.Calendar

class DoctorDescription : AppCompatActivity() {
    lateinit var binding: ActivityDoctorDescriptionBinding
    lateinit var doctor: Doctor
    private lateinit var dbReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbReference = Firebase.database.reference

        var doctor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("DOCTOR", Doctor::class.java)
        } else {
            intent.getParcelableExtra("DOCTOR")
        }
        doctor?.let {
            binding.dName.text = it.name
            binding.dSpeciality.text = it.speciality
            binding.tvRating3.text = it.rating.toString()
            binding.tvDistance3.text = it.distance
            binding.phone.text = it.phone
            binding.docInfo.text = it.intro
            binding.clinicName.text = it.place
            binding.address.text = it.address
            binding.fees.text = "$${it.fees.toString()} consultation fees at clinic"
            binding.tvDays.text = it.days
            binding.tvTime.text = it.timing
        }
        binding.ivCal.setOnClickListener {
            datePickerDialog()
        }

        binding.ivClock.setOnClickListener {
            timePickerDialog()
        }

        binding.layoutBookApp.setOnClickListener {
            Toast.makeText(
                this, """
                Your Appointment booked successfully:
                ${binding.etDate.text} || ${binding.etTime.text}
            """.trimIndent(), Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(this, HomePage::class.java))
        }

    }

    private fun datePickerDialog() {
        var calender = Calendar.getInstance()
        var dayOfMonth = calender.get(Calendar.DAY_OF_MONTH)
        var month = calender.get(Calendar.MONTH)
        var year = calender.get(Calendar.YEAR)

        var datePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                var date = "${dayOfMonth.toString().padStart(2, '0')}/${
                    (month + 1).toString().padStart(2, '0')
                }/${year}"
                binding.etDate.setText(date)
            },
            year,
            month,
            dayOfMonth
        )
        datePicker.show()
    }

    private fun timePickerDialog() {

        var calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR)
        var minute = calendar.get(Calendar.MINUTE)

        var timePicker =
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                var time =
                    "${hourOfDay.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
                binding.etTime.setText(time)
            }, hour, minute, true)
        timePicker.show()
    }
}