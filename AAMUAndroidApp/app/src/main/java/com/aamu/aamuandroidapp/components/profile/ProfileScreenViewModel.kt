package com.aamu.aamuandroidapp.components.profile

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUBBSResponse
import com.aamu.aamuandroidapp.data.api.response.AAMUGarmResponse
import com.aamu.aamuandroidapp.data.api.response.AAMUPlannerSelectOne
import com.aamu.aamuandroidapp.data.api.response.AAMUUserInfo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileScreenViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileScreenViewModel(context) as T
    }
}

class ProfileScreenViewModel (context: Context) : ViewModel(){
    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    private val context = context

    val userinfo = MutableLiveData<AAMUUserInfo>()
    val userGramdata = MutableLiveData<List<AAMUGarmResponse>>()
    val userGramphoto = MutableLiveData<List<String>>()

    val plannerSelectList = MutableLiveData<List<AAMUPlannerSelectOne>>()
    val bookMarkSelectList = MutableLiveData<List<AAMUBBSResponse>>()

    val errorLiveData = MutableLiveData<String>()
    val errorBookMarkLiveData = MutableLiveData<String>()
    val errorGramLiveData = MutableLiveData<String>()

    fun getUserInfo(){
        val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
        val userid : String? = preferences.getString("id",null)
        viewModelScope.launch {
            aamuRepository.getUserInfo(userid?:"")
                .collect{ reuserinfo->
                    if(reuserinfo.id != null){
                        userinfo.value = reuserinfo
                    }
                    else{
                        errorLiveData.value = "개인정보를 받아오는데 실페했습니다"
                    }
                }
        }
    }
    fun getUserGram(){
        val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
        val userid : String? = preferences.getString("id",null)
        viewModelScope.launch {
            aamuRepository.getGramBySearch("id",userid!!)
                .collect{ garmlist ->
                    if(garmlist.isNotEmpty()){
                        userGramdata.value = garmlist
                        val tempList = ArrayList<String>()
                        for(gram in garmlist){
                            if(gram.photo?.isNotEmpty() == true) {
                                for (item in gram.photo!!) {
                                    tempList.add(item)
                                }
                            }
                        }
                        userGramphoto.value = tempList
                    }
                    else{
                        errorGramLiveData.value = "개인정보를 받아오는데 실페했습니다"
                    }
                }
        }
    }

    fun getPlannerSelectList(){
        viewModelScope.launch {
            aamuRepository.getPlannerSelectList()
                .collect{aamuListPlanner->
                    if (aamuListPlanner.isNotEmpty()){
                        plannerSelectList.value = aamuListPlanner
                    }
                    else{
                        errorLiveData.value = "리스트를 받아오는데 실페했습니다"
                    }
                }
        }
    }

    fun getPlannerBookMarkSelectList(){
        val preferences : SharedPreferences = context.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
        val userid : String? = preferences.getString("id",null)
        viewModelScope.launch {
            aamuRepository.getPlannerBookMarkSelectList(userid ?: "")
                .collect {
                    if(it.isNotEmpty()) {
                        bookMarkSelectList.value = it
                    }
                    else{
                        errorBookMarkLiveData.value = "북마크한 리스트가 없습니다"
                    }
                }
        }
    }


}