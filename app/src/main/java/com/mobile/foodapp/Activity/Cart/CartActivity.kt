package com.mobile.foodapp.Activity.Cart

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mobile.foodapp.Activity.BaseActivity
import com.mobile.foodapp.Helper.ManagmentCart
import com.mobile.foodapp.R


class CartActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CartScreen (ManagmentCart(this), onBackClick = {finish()})
        }
    }
}

@Composable
fun CartScreen(managementCart: ManagmentCart=ManagmentCart(LocalContext.current), onBackClick:() -> Unit) {
    var cartItems by remember { mutableStateOf(ArrayList(managementCart.getListCart())) }
    var refreshTrigger by remember { mutableStateOf(0) }
    val tax = remember { mutableStateOf(0.0) }

    LaunchedEffect(refreshTrigger) {
        cartItems = ArrayList(managementCart.getListCart())
        calculatorCart(managementCart, tax)
    }

    LazyColumn (modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        item {
            ConstraintLayout(modifier = Modifier.padding(top = 36.dp)) {
                val (backBtn, cartTxt) = createRefs()
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(cartTxt) {centerTo(parent)},
                    text = "Your Cart",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
                Image(painter = painterResource(R.drawable.back_grey),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(backBtn) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                        .clickable { onBackClick() }
                )
            }
        }
        if (cartItems.isEmpty()) {
            item {
                Text(
                    text = "Cart Is Empty",
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            items(cartItems) { item ->
                CartItem(
                    cartItems = cartItems,
                    item = item,
                    managementCart = managementCart,
                    onItemChange = {
                        refreshTrigger += 1
                    }
                )
            }

            item {
                Text(
                    text = "Order Summary",
                    color = colorResource(R.color.darkPurple),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            item {
                CartSummary(
                    itemTotal = managementCart.getTotalFee(),
                    tax = tax.value,
                    delivery = 10.0
                )
            }

            item {
                Text(
                    text = "Information",
                    color = colorResource(R.color.darkPurple),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            item {
                DeliveryInfoBox(managementCart)
            }
        }
    }
}

fun calculatorCart(managementCart: ManagmentCart, tax: MutableState<Double>) {
    val percentageTax = 0.02
    tax.value = Math.round((managementCart.getTotalFee() * percentageTax) * 100) / 100.0
}