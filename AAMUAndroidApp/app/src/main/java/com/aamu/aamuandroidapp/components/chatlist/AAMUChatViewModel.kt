package com.aamu.aamuandroidapp.components.chatlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aamu.aamuandroidapp.components.aamuplan.AAMUPlanViewModel

class AAMUChatViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AAMUChatViewModel(context) as T
    }
}

class AAMUChatViewModel(context: Context){
    
}