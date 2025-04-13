package com.mobile.foodapp.Repository

import com.google.firebase.database.FirebaseDatabase
import com.mobile.foodapp.Domain.OrderModel
import kotlinx.coroutines.tasks.await

class OrderRepository {
    private val database = FirebaseDatabase.getInstance()
    private val ordersRef = database.getReference("orders")

    suspend fun pushOrder(order: OrderModel): Boolean {
        return try {
            ordersRef.child(order.orderId).setValue(order).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getOrders(): List<OrderModel> {
        return try {
            val snapshot = ordersRef.get().await()
            val orders = mutableListOf<OrderModel>()
            for (childSnapshot in snapshot.children) {
                childSnapshot.getValue(OrderModel::class.java)?.let {
                    orders.add(it)
                }
            }
            orders
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
} 