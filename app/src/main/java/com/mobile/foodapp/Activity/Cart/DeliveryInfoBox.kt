package com.mobile.foodapp.Activity.Cart

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.foodapp.Domain.OrderModel
import com.mobile.foodapp.Helper.ManagmentCart
import com.mobile.foodapp.Helper.TinyDB
import com.mobile.foodapp.R
import com.mobile.foodapp.Repository.OrderRepository
import java.util.UUID
import kotlinx.coroutines.launch

@Composable
fun DeliveryInfoBox(managementCart: ManagmentCart) {
    val context = LocalContext.current
    val tinyDB = TinyDB(context)
    val cartItems = managementCart.getListCart()
    val totalAmount = managementCart.getTotalFee()
    val scope = rememberCoroutineScope()
    val orderRepository = remember { OrderRepository() }
    var isLoading by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(color = colorResource(R.color.grey), shape = RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        InfoItem(
            title = "Your Deliver Address",
            content = "Arad",
            icon = painterResource(R.drawable.location)
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))
        InfoItem(
            title = "Payment Method",
            content = "Cash",
            icon = painterResource(R.drawable.credit_card)
        )
    }

    Button(
        onClick = {
            if (cartItems.isEmpty()) {
                Toast.makeText(context, "Your cart is empty", Toast.LENGTH_SHORT).show()
                return@Button
            }

            isLoading = true
            scope.launch {
                val newOrder = OrderModel(
                    orderId = UUID.randomUUID().toString(),
                    items = ArrayList(cartItems),
                    totalAmount = totalAmount,
                    deliveryAddress = "Arad",
                    paymentMethod = "Cash"
                )

                val success = orderRepository.pushOrder(newOrder)
                if (success) {
                    // Save to local storage as well
                    val orders = tinyDB.getOrderListObject("Orders") ?: ArrayList()
                    orders.add(newOrder)
                    tinyDB.putOrderListObject("Orders", orders)
                    
                    // Clear cart
                    managementCart.clearCart()
                    Toast.makeText(context, "Order placed successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to place order. Please try again.", Toast.LENGTH_SHORT).show()
                }
                isLoading = false
            }
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.orange)
        ),
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxWidth()
            .height(50.dp),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text(text = "Place Order", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun InfoItem(title: String, content: String, icon: Painter) {
    Column {
        Text(text = title, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Row (verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = icon,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = content, fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}