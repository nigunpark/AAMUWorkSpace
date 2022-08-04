package com.aamu.aamuandroidapp.components.gram.image

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUGarmResponse
import kotlinx.coroutines.launch

class PlanGramImageGridViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlanGramImageGridViewModel() as T
    }
}

class PlanGramImageGridViewModel() : ViewModel(){
    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val contentidByGarmList = MutableLiveData<List<String>>()
    val errorLiveData = MutableLiveData<String>()

    fun getGramPhotoList(contentid : Int){
        viewModelScope.launch {
            aamuRepository.getGramByPlaceList(contentid)
                .collect{ gramList ->
                    if (gramList.isNotEmpty()){
                        Log.i("com.aamu.aamu","gramList : " +gramList)
                        val tempList = ArrayList<String>()
                        for (gramItem : AAMUGarmResponse in gramList){
                            if(gramItem.photo?.isNotEmpty()==true) {
                                for (gramPhotoList: String in gramItem?.photo!!) {
                                    tempList.add(gramPhotoList)
                                }
                            }
                        }
                        Log.i("com.aamu.aamu","tempList : " +tempList)
                        if(tempList.size==0){
                            errorLiveData.value = "저장된 사진들이 없어요!!"
                        }
                        else{
                            contentidByGarmList.value = tempList
                        }

                    }
                    else{
                        errorLiveData.value = "저장된 사진들이 없어요!!"
                    }
                }
        }
    }
}