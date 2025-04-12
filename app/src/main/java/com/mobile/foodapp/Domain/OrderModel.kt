package com.mobile.foodapp.Domain

import java.util.Date

data class OrderModel(
    val orderId: String = "",
    val items: ArrayList<FoodModel> = ArrayList(),
    val totalAmount: Double = 0.0,
    val deliveryAddress: String = "",
    val paymentMethod: String = "",
    val orderDate: Date = Date(),
    val status: String = "Pending" // Pending, Processing, Delivered, Cancelled
) 