package com.example.medicsapplication.Models

data class Order(
    var id:String? = null,
    var medImg:String? = null,
    var medName:String? = null,
    var medQty:String? = null,
    var orderQty:String? = null,
    var price:Int? = 0,
)
