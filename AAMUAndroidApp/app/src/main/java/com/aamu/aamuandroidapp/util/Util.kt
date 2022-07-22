package com.aamu.aamuandroidapp.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Activity.setStatusBarTransparent() {
    window.apply {
        setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
    if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(this.window , window.decorView ).let {
            it.hide(WindowInsetsCompat.Type.navigationBars())
            it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

    }
}

fun Activity.setStatusBarOrigin() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
    if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(this.window , window.decorView).show(WindowInsetsCompat.Type.navigationBars())
    }
}
lateinit var contextL : Context
fun setContextapp(context: Context){
    contextL = context
}

fun getToken() : String?{
    val preferences : SharedPreferences = contextL.getSharedPreferences("usersInfo", Context.MODE_PRIVATE)
    val token : String? = preferences.getString("token",null)
    return token
}




//fun Activity.hideSystemUI() {
//    activity?.let { WindowCompat.setDecorFitsSystemWindows(it?.window, false) }
//    activity?.window?.let {
//        WindowInsetsControllerCompat(it, binding.root).let { controller ->
//            controller.hide(WindowInsetsCompat.Type.systemBars())
//            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }
//    }
//}
//
//fun showSystemUI() {
//    activity?.window?.let { WindowCompat.setDecorFitsSystemWindows(it, true) }
//    activity?.window?.let {
//        WindowInsetsControllerCompat(
//            it,
//            binding.root
//        ).show(WindowInsetsCompat.Type.systemBars())
//    }
//}
