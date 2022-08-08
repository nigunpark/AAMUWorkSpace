package com.aamu.aamuandroidapp.components.chatlist

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUChatRoomResponse
import kotlinx.coroutines.launch

class AAMUChatViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AAMUChatViewModel(context) as T
    }
}

class AAMUChatViewModel(context: Context) : ViewModel(){
    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    private val context = context

    val chatingRoomList = MutableLiveData<List<AAMUChatRoomResponse>>(emptyList())
    val errorLiveData = MutableLiveData<String>()

    fun getChatingList(userid : String){
        viewModelScope.launch {
            aamuRepository.getChatRoomList(userid)
                .collect{ chatinglist->
                    if(chatinglist.isNotEmpty()){
                        chatingRoomList.value = chatinglist
                    }
                    else{
                        errorLiveData.value = "아직 사용자와 채팅한 내역이 없어요"
                    }
                }
        }

    }
}