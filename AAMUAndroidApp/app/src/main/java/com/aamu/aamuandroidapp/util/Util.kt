package com.aamu.aamuandroidapp.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import java.util.*
import java.util.concurrent.TimeUnit

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

fun getLatLng(): Location? {
    var currentLatLng: Location? = null
    var hasFineLocationPermission = ContextCompat.checkSelfPermission(
        contextL,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    var hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
        contextL,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val criteria = Criteria().apply {
        accuracy = Criteria.ACCURACY_FINE
    }
    val locatioNManager = contextL.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    val locatioNProvider = locatioNManager?.getBestProvider(criteria,true)

    if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
        hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
    ) {
        currentLatLng = locatioNManager?.getLastKnownLocation(locatioNProvider!!)
    }

    return currentLatLng
}

lateinit var stomp : CustomStompClient
private lateinit var stompConnection: Disposable

fun stompConnection() {
    val url = "ws://192.168.0.22:8080/aamurest/ws/chat/websocket"
    val intervalMillis = 1000L
    val client = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()

    stomp = CustomStompClient(client,intervalMillis).apply {
        this@apply.url = url
    }

    stompConnection = stomp.connect().subscribe() {
        when (it.type) {
            Event.Type.OPENED -> {
                Log.i("com.aamu.aamu","열림")
            }
            Event.Type.CLOSED -> {
                Log.i("com.aamu.aamu","닫힘")
            }
            Event.Type.ERROR -> {
                Log.i("com.aamu.aamu","에러")
            }
            else -> {}
        }
    }
}

fun stompDisconnect(){
    stompConnection.dispose()
}

fun getContentTypeId(contenttypeid : Int) : String{
    return when (contenttypeid) {
        12 -> "광장지"
        14 -> "문화시설"
        15 -> "행사/공연/축제"
        25 -> "여행코스"
        28 -> "레포츠"
        32 -> "숙박"
        38 -> "쇼핑"
        39 -> "음식점"
        else -> "알수 없음"
    }
}

fun displayedAt(item : Long) : String{
    val latetime = Calendar.getInstance().time.time - item
    val seconds = latetime / 1000.0
    if (seconds < 60) return "방금 전"
    val minutes = seconds / 60
    if (minutes < 60) return "${Math.floor(minutes).toInt()}분 전"
    val hours = minutes / 60
    if (hours < 24) return "${Math.floor(hours).toInt()}시간 전"
    val days = hours / 24
    if (days < 7) return "${Math.floor(days).toInt()}일 전"
    val weeks = days / 7
    if (weeks < 5) return "${Math.floor(weeks).toInt()}주 전"
    val months = days / 30
    if (months < 12) return "${Math.floor(months).toInt()}개월 전"
    val years = days / 365
    return "${Math.floor(years).toInt()}년 전"
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
