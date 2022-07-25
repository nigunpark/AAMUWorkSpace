package com.aamu.aamuandroidapp

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.aamu.aamuandroidapp.databinding.ActivityMainBinding
import com.aamu.aamuandroidapp.util.setContextapp
import com.kakao.sdk.common.KakaoSdk
import java.security.MessageDigest


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContextapp(applicationContext)
        navController = findNavController(R.id.nav_host_fragment)

        try {
            val information =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                information.signingInfo.apkContentsSigners
            } else {
                TODO("VERSION.SDK_INT < P")
            }
            val md = MessageDigest.getInstance("SHA")
            for (signature in signatures) {
                val md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                var hashcode = String(Base64.encode(md.digest(), 0))
                Log.d("hashcode", "" + hashcode)
            }
        } catch (e: Exception) {
            Log.d("hashcode", "에러::" + e.toString())

        }
        KakaoSdk.init(this, "c55ed90259ce1795a67bdc259d5642f1")
    }


}