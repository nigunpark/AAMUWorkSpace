package com.aamu.aamuandroidapp.components.gram.detail

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.components.gram.image.PlanGramImageGridViewModel
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUGarmResponse
import kotlinx.coroutines.launch

class GramDetailViewModelFactory(val context : Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GramDetailViewModel(context) as T
    }
}

class GramDetailViewModel(context : Context) : ViewModel(){
    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val gramDetail = MutableLiveData<AAMUGarmResponse>()
    val errorLiveData = MutableLiveData<String>()

    val context = context

    fun getGramDetail(lno : Int){
        viewModelScope.launch {
            val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
            val userid : String? = preferences.getString("id",null)
            aamuRepository.getGramDetail(userid.toString(),lno)
                .collect {gramitem->
                    if(gramitem.lno != null){
                        gramDetail.value = gramitem
                    }
                    else{
                        errorLiveData.value = "상세보기 받아오는데 실패했습니다"
                        Toast.makeText(context,"상세보기 받아오는데 실패습니다", Toast.LENGTH_LONG)
                    }
                }
        }
    }
}