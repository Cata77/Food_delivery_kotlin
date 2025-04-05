package com.mobile.foodapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mobile.foodapp.Domain.BannerModel
import com.mobile.foodapp.Domain.CategoryModdel
import com.mobile.foodapp.Domain.FoodModel
import com.mobile.foodapp.Repository.MainRepository

class MainViewModel : ViewModel(){
    private val repository = MainRepository()

    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        return repository.loadBanner()
    }

    fun loadCategory(): LiveData<MutableList<CategoryModdel>> {
        return repository.loadCategory()
    }

    fun loadFiltered(id:String): LiveData<MutableList<FoodModel>> {
        return repository.loadFiltered(id)
    }
}