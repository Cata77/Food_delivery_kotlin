package com.mobile.foodapp.Activity.Dashboard

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobile.foodapp.Activity.Cart.CartActivity
import com.mobile.foodapp.Activity.Order.OrderActivity
import com.mobile.foodapp.R

@Composable
@Preview
fun MyBottomBar() {
    val context = LocalContext.current
    var selectedItem by remember { mutableStateOf("Home") }
    val bottomMenuItemsList = prepareBottomMenu()

    BottomAppBar(
        contentColor = colorResource(R.color.grey),
        tonalElevation = 3.dp
    ) {
        bottomMenuItemsList.forEach{bottomMenuItem ->
            NavigationBarItem(
                selected = (selectedItem == bottomMenuItem.label),
                onClick = {
                    selectedItem = bottomMenuItem.label
                    when (bottomMenuItem.label) {
                        "Cart" -> context.startActivity(Intent(context, CartActivity::class.java))
                        "Order" -> context.startActivity(Intent(context, OrderActivity::class.java))
                        else -> Toast.makeText(context, bottomMenuItem.label, Toast.LENGTH_SHORT).show()
                    }
                },
                icon = {
                    Icon(painter = bottomMenuItem.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(24.dp))
                }
            )
        }
    }
}

data class BottomMenuItem(
    val label: String, val icon: Painter
)

@Composable
fun prepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()

    bottomMenuItemList.add(BottomMenuItem(label = "Home", icon = painterResource(R.drawable.btn_1)))
    bottomMenuItemList.add(BottomMenuItem(label = "Cart", icon = painterResource(R.drawable.btn_2)))
    bottomMenuItemList.add(BottomMenuItem(label = "Favorite", icon = painterResource(R.drawable.btn_3)))
    bottomMenuItemList.add(BottomMenuItem(label = "Order", icon = painterResource(R.drawable.btn_4)))
    bottomMenuItemList.add(BottomMenuItem(label = "Profile", icon = painterResource(R.drawable.btn_5)))

    return bottomMenuItemList
}