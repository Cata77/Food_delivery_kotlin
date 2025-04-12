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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import java.util.UUID

@Composable
fun DeliveryInfoBox(managementCart: ManagmentCart) {
    val context = LocalContext.current
    val tinyDB = TinyDB(context)
    val cartItems = managementCart.getListCart()
    val totalAmount = managementCart.getTotalFee()

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

            val orders = tinyDB.getOrderListObject("Orders") ?: ArrayList()
            val newOrder = OrderModel(
                orderId = UUID.randomUUID().toString(),
                items = ArrayList(cartItems),
                totalAmount = totalAmount,
                deliveryAddress = "Arad",
                paymentMethod = "Cash"
            )
            orders.add(newOrder)
            tinyDB.putOrderListObject("Orders", orders)
            managementCart.clearCart()
            Toast.makeText(context, "Order placed successfully", Toast.LENGTH_SHORT).show()
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.orange)
        ),
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = "Place Order", fontSize = 18.sp, color = Color.White)
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