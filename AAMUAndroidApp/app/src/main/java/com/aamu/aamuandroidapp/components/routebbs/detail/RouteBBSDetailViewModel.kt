package com.aamu.aamuandroidapp.components.routebbs.detail

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.components.gram.AAMUgramViewModel
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUBBSResponse
import com.aamu.aamuandroidapp.data.api.response.AAMUPlannerSelectOne
import com.aamu.aamuandroidapp.data.api.response.Review
import com.aamu.aamuandroidapp.util.contextL
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RouteBBSDetailViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RouteBBSDetailViewModel(context) as T
    }
}

class RouteBBSDetailViewModel(context: Context) : ViewModel(){

    private val context = context

    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val bbsDetail = MutableLiveData<AAMUBBSResponse>()
    val plannerDetail = MutableLiveData<AAMUPlannerSelectOne>()
    val errorLiveData = MutableLiveData<String>()
    val errorplannerLiveData = MutableLiveData<String>()

    fun getBBSListOne(rbn : Int){
        viewModelScope.launch {
            aamuRepository.getBBSOne(rbn)
                .collect{ bbsitem ->
                    if (bbsitem.rbn != null){
                        bbsDetail.value = bbsitem
                    }
                    else{
                        errorLiveData.value = "자세히 보기를 받아오는데 실패했습니다"
                        Toast.makeText(context,"자세히 보기를 받아오는데 실패했습니다", Toast.LENGTH_LONG)
                    }
                }
        }
        viewModelScope.launch {
            aamuRepository.getPlannerSelectOne(rbn)
                .collect{ plannerItem ->
                    if(plannerItem.rbn != null){
                        plannerDetail.value = plannerItem
                    }
                    else{
                        errorplannerLiveData.value = "경로를 받아오는데 실패했습니다"
                        Toast.makeText(context,"경로를 받아오는데 실패했습니다", Toast.LENGTH_LONG)
                    }
                }
        }
    }

    fun postReview(rbn : Int,rating : Float,reviewContent : String){
        val preferences : SharedPreferences = contextL.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
        val userid : String? = preferences.getString("id",null)
        viewModelScope.launch{

            val review = Review(rbn = rbn,id= userid, rate = rating, review = reviewContent)
            aamuRepository.postReview(review)
                .collect{
                    if(it.get("result").toString().equals("insertSuccess")){
                        getBBSListOne(rbn)
                    }
                    else{
                        Toast.makeText(context,"리뷰를 쓰는데 실패했어요", Toast.LENGTH_LONG)
                    }
                }
        }
    }
}