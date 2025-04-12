package com.mobile.foodapp.Activity.Order

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.foodapp.Activity.BaseActivity
import com.mobile.foodapp.Domain.OrderModel
import com.mobile.foodapp.Helper.TinyDB
import com.mobile.foodapp.R

class OrderActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderScreen(onBackClick = { finish() })
        }
    }
}

@Composable
fun OrderScreen(onBackClick: () -> Unit) {
    val tinyDB = TinyDB(LocalContext.current)
    val orders = remember { 
        mutableStateOf(tinyDB.getOrderListObject("Orders") ?: ArrayList()) 
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            val (backBtn, title) = createRefs()
            
            Image(
                painter = painterResource(R.drawable.back_grey),
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { onBackClick() }
                    .constrainAs(backBtn) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Text(
                text = "My Orders",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }

        if (orders.value.isEmpty()) {
            Text(
                text = "No orders yet",
                color = colorResource(R.color.grey),
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            LazyColumn {
                items(orders.value) { order ->
                    OrderItem(order = order)
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: OrderModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Order #${order.orderId}",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = "Total: $${order.totalAmount}",
            color = colorResource(R.color.darkPurple)
        )
        Text(
            text = "Status: ${order.status}",
            color = colorResource(R.color.orange)
        )
        Text(
            text = "Date: ${order.orderDate}",
            color = colorResource(R.color.grey)
        )
    }
} 