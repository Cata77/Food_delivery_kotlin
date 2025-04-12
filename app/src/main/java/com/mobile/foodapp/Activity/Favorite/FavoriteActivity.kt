package com.mobile.foodapp.Activity.Favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.mobile.foodapp.Activity.BaseActivity
import com.mobile.foodapp.Activity.DetailEachFood.DetailEachFoodActivity
import com.mobile.foodapp.Domain.FoodModel
import com.mobile.foodapp.Helper.TinyDB
import com.mobile.foodapp.R

class FavoriteActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FavoriteScreen(onBackClick = { finish() })
        }
    }
}

@Composable
fun FavoriteScreen(onBackClick: () -> Unit) {
    val tinyDB = TinyDB(LocalContext.current)
    val favorites = remember { 
        mutableStateOf(tinyDB.getFavoriteListObject() ?: ArrayList()) 
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
                text = "My Favorites",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }

        if (favorites.value.isEmpty()) {
            Text(
                text = "No favorite items yet",
                color = colorResource(R.color.grey),
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            LazyColumn {
                items(favorites.value) { food ->
                    FavoriteItem(food = food)
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(food: FoodModel) {
    val context = LocalContext.current
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(colorResource(R.color.grey), RoundedCornerShape(10.dp))
            .clickable {
                val intent = Intent(context, DetailEachFoodActivity::class.java).apply {
                    putExtra("object", food)
                }
                ContextCompat.startActivity(context, intent, null)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = food.ImagePath,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = food.Title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$${food.Price}",
                color = colorResource(R.color.darkPurple),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
} 