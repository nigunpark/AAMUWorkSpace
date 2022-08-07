package com.aamu.aamuandroidapp.fragment.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import meow.bottomnavigation.MeowBottomNavigation

class MainViewModelFactory(val context : Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }
}

class MainViewModel(context : Context) : ViewModel() {

    val fromthan = MutableLiveData<String>()
    val no = MutableLiveData<Int>()

    val meowBottom = MeowBottomNavigation(context)

    val clickno = MutableLiveData<Int>(1)


    fun setFromthanNo(fromthanFun : String,noFun : Int ){
        fromthan.value = fromthanFun
        no.value = noFun
    }
}