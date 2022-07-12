package com.aamu.aamuandroidapp.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import java.util.*

open class PermissionUtils(private val context : Context,
                           private val  activity : Activity,
                           private val  requestPermissions : Array<String>) {

    //권한요청시 각 권한을 구분하기 위한 요청코드값(식별자)
    public val MY_PERMISSIONS = 1000

    private var deniedPermissions : MutableCollection<String> = Vector<String>()

    fun checkPermission(): Boolean {
        deniedPermissions.clear()
        for (permission in requestPermissions) {
            val checkPermission = ActivityCompat.checkSelfPermission(
                context,
                permission!!
            )
            //권한이 없는 경우 deniedPermissions에 저장
            if (checkPermission == PackageManager.PERMISSION_DENIED) deniedPermissions.add(
                permission
            )
        }
        return if (!deniedPermissions.isEmpty()) {
            false
        } else true
    }

    //사용자에게 권한 허용 요청
    fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity,
            deniedPermissions.toTypedArray(),
            MY_PERMISSIONS
        )
    }

    //요청한 권한에 대한 결과값 처리
    fun requestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ): Boolean {
        if (requestCode == MY_PERMISSIONS && grantResults.size > 0) {
            deniedPermissions.clear()
            for (i in grantResults.indices) {
                //grantResults 가
                // 0이면 사용자가 허용
                //-1이면 사용자가 거부
                //permissions중에 하나라도 허용하지 않는 권한이 있다면 false를 리턴
                if (grantResults[i] == -1) {
                    deniedPermissions.add(permissions[i]!!)
                }
            }
            if (!deniedPermissions.isEmpty()) {
                return false
            }
        }
        return true
    }
}