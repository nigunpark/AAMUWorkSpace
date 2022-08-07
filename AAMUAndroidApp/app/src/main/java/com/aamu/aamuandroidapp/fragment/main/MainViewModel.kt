package com.aamu.aamuandroidapp.fragment.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory() : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}

class MainViewModel : ViewModel() {

    val fromthan = MutableLiveData<String>()
    val no = MutableLiveData<Int>()

    fun setFromthanNo(fromthanFun : String,noFun : Int ){
        fromthan.value = fromthanFun
        no.value = noFun
    }
}