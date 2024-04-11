package com.example.medicsapplication.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Doctor(
    var id: String? = null,
    var imgUrl:String? = null,
    var name: String? = null,
    var speciality: String? = null,
    var distance: String = "800m away",
    var intro: String? = null,
    var place: String? = null,
    var phone: String? = null,
    var address: String? = null,
    var timing: String? = null,
    var rating: Float? = 0.0f,
    var fees: Int? = null,
    var createdAt:Long=System.currentTimeMillis(),
    var days:String? = null,
):Parcelable
