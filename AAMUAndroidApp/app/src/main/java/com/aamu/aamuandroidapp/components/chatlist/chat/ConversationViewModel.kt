package com.aamu.aamuandroidapp.components.chatlist.chat

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aamu.aamuandroidapp.data.api.AAMUDIGraph
import com.aamu.aamuandroidapp.data.api.repositories.AAMURepository
import com.aamu.aamuandroidapp.data.api.response.AAMUChatingMessageResponse
import com.aamu.aamuandroidapp.util.contextL
import com.aamu.aamuandroidapp.util.stomp
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap


class ConversationViewModelFactory() : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ConversationViewModel() as T
    }
}

class ConversationViewModel() : ViewModel() {
    private val aamuRepository : AAMURepository = AAMUDIGraph.createAAMURepository()

    val messageList = MutableLiveData<List<AAMUChatingMessageResponse>>()
    val errorLiveData = MutableLiveData<String>()

    private val map : HashMap<String,String> = HashMap()

    private lateinit var topic: Disposable

    fun getChatList(roomno : String){
        map.put("roomno",roomno)
        viewModelScope.launch {
            aamuRepository.getChatMessageList(map)
                .collect{aamumessageList->
                    if (aamumessageList.isNotEmpty()){
                        messageList.value = aamumessageList
                    }
                    else{
                        errorLiveData.value = "리스트를 받아오는데 실페했습니다"
                        Toast.makeText(contextL,"리스트를 받아오는데 실페했습니다", Toast.LENGTH_LONG)
                    }
                }
        }
        subscribe()
    }

    fun sendMessage(content : String){
        val preferences : SharedPreferences = contextL.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
        val id : String? = preferences.getString("id",null)
        val authpro : String? = preferences.getString("profile",null)
        map.put("authid",id ?: "");
        map.put("missage",content)
        val dateTime: Date = Calendar.getInstance().time
        val dateTimeAsLong: Long = dateTime.time
        map.put("senddate",dateTimeAsLong.toString())
        map.put("authpro",authpro ?: "")
        val mapper = jacksonObjectMapper()

        stomp.send("/app/chat/message",mapper.writeValueAsString(map)).subscribe{
            Log.i("com.aamu.aamu","뭔가가 : " + it.toString())
        }
    }

    fun subscribe(){
        val preferences : SharedPreferences = contextL.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
        val userid : String? = preferences.getString("id",null)
        topic = stomp.join("/queue/chat/message/"+map.get("roomno"),userid.toString()).subscribe{

            val mapper = jacksonObjectMapper()
            val message : AAMUChatingMessageResponse = mapper.readValue(it,AAMUChatingMessageResponse::class.java)

            Log.i("com.aamu.aamu","뭔가옴 : " + message)
            val tempList : MutableList<AAMUChatingMessageResponse>? = messageList.value?.toMutableList()
            tempList?.add(0,message)
            messageList.postValue(tempList?.toList())
        }
    }

    fun unSubscribe(){
        topic.dispose()
    }
}