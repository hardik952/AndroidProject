package com.example.medicsapplication.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var id: String? = null,
    var imgUrl: String? = null,
    var name: String? = null,
    var desc: String? = null,
    var quantity: String? = null,
    var price: Int? = 0,
    var discount: Int = 0,
    var mfg: String? = null,
    var rating: Float? = 0.0f,
    var form: String? = null,
    var benefits: String? = null,
    var safety: String? = "Read the label carefully before use.Store in a cool, dry and dark place, below 25 degree Celsius.Keep out of reach of children.",
    var createdAt: Long = System.currentTimeMillis(),
    var disAmount: Int = price!! * discount / 100,
    var disPrice: Int = price!! - disAmount,
    ) : Parcelable