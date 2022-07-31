package com.aamu.aamuandroidapp.components.gram

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUGarmResponse
import kotlinx.coroutines.launch

class AAMUgramViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AAMUgramViewModel(context) as T
    }
}

class  AAMUgramViewModel(context : Context) : ViewModel(){
    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val aamuGramList = MutableLiveData<List<AAMUGarmResponse>>()
    val errorLiveData = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
            val userid : String? = preferences.getString("id",null)
            aamuRepository.getGramList(userid.toString())
                .collect{ aamuGram ->
                    if(aamuGram.isNotEmpty()){
                        aamuGramList.value = aamuGram
                    }
                    else{
                        errorLiveData.value = "리스트를 받아오는데 실페했습니다"
                        Toast.makeText(context,"리스트를 받아오는데 실페했습니다", Toast.LENGTH_LONG)
                    }
                }
        }
    }
}