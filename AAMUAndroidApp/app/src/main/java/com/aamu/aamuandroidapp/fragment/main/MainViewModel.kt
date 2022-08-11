package com.aamu.aamuandroidapp.fragment.main

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUNotiResponse
import com.aamu.aamuandroidapp.fragment.main.sub.GramFragment
import com.aamu.aamuandroidapp.fragment.main.sub.HomeFragment
import com.aamu.aamuandroidapp.fragment.main.sub.InfoFragment
import com.aamu.aamuandroidapp.fragment.main.sub.RouteBBSFragment
import com.aamu.aamuandroidapp.util.contextL
import com.aamu.aamuandroidapp.util.stomp
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch
import meow.bottomnavigation.MeowBottomNavigation

class MainViewModelFactory(val context : Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }
}

class MainViewModel(context : Context) : ViewModel() {

    val fromthan = MutableLiveData<String>()
    val no = MutableLiveData<Int>()
    val context = context
    val meowBottom = MeowBottomNavigation(context)

    val noticount = MutableLiveData<Int>(0)

    private lateinit var topic: Disposable

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val notilist = MutableLiveData<List<AAMUNotiResponse>>()

    val errorLiveData = MutableLiveData<String>()

    init {
        meowBottom.apply {
            this.apply {
                hasAnimation =true
                backgroundBottomColor = Color.parseColor("#ffffff")
                circleColor = Color.parseColor("#ffffff")
                countBackgroundColor = Color.parseColor("#ff6f00")
                countTextColor = Color.parseColor("#ffffff")
                defaultIconColor = Color.parseColor("#90a4ae")
//                rippleColor = Color.parseColor("#2f424242")
                selectedIconColor = Color.parseColor("#3c415e")
//                shadowColor = Color.parseColor("#1f212121")
            }

            this.apply {
                add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
                add(MeowBottomNavigation.Model(2, R.drawable.ic_explore))
                add(MeowBottomNavigation.Model(3, R.drawable.ic_instagram))
                add(MeowBottomNavigation.Model(4, R.drawable.ic_notification))
                add(MeowBottomNavigation.Model(5, R.drawable.ic_account))
                show(1)
            }
        }
        getNotiList()
    }

    fun show(id : Int){
        meowBottom.show(id)
    }

    fun setFromthanNo(fromthanFun : String,noFun : Int ){
        fromthan.value = fromthanFun
        no.value = noFun
    }

    fun subNotification(){
        val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)!!
        val userid : String? = preferences.getString("id",null)
        topic = stomp.join("/queue/notification/"+userid).subscribe{

            val mapper = jacksonObjectMapper()
            val message : AAMUNotiResponse = mapper.readValue(it, AAMUNotiResponse::class.java)
            putNotiList(message)
        }
    }
    fun unSubNotification(){
        topic.dispose()
    }

    fun putNotiList(item : AAMUNotiResponse){
        viewModelScope.launch {
            val tempNotiList = notilist.value?.toMutableList()
            tempNotiList?.add(0, item)
            if (tempNotiList != null) {
                notilist.value = tempNotiList.toList()
                setNotiCount()
            }
        }
    }

    fun getNotiList(){
        noticount.postValue(0)
        viewModelScope.launch {
            val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
            val userid : String? = preferences.getString("id",null)
            aamuRepository.getNotiList(userid!!)
                .collect{ respNotiList->
                    if(respNotiList.isNotEmpty()){
                        notilist.value = respNotiList
                        setNotiCount()
                    }
                    else{
                        errorLiveData.value = "알람이 없어요"
                        Toast.makeText(context,"리스트를 받아오는데 실페했습니다", Toast.LENGTH_LONG)
                    }
                }
        }
    }

    fun delNotiList(nano : Int){
        viewModelScope.launch {
            val tempNotiList = notilist.value?.toMutableList()
            if (tempNotiList != null) {
                for (item in tempNotiList) {
                    if (item.nano == nano) {
                        item.readknow = 1
                        break
                    }
                }
                setNotiCount()
            }
        }
    }

    fun updateNotiList(nano : Int){
        viewModelScope.launch {
            val tempNotiList = notilist.value?.toMutableList()
            if (tempNotiList != null) {
                for (item in tempNotiList) {
                    if (item.nano == nano) {
                        item.readknow = 1
                        break
                    }
                }
                notilist.value = emptyList()
                notilist.value = tempNotiList.toList()
                setNotiCount()
            }
        }
    }

    fun setNotiCount(){
        viewModelScope.launch {
            noticount.value = 0
            for (item in notilist.value!!){
                if(item.readknow == 0){
                    noticount.value = noticount.value?.plus(1)
                }
            }
        }
    }
}