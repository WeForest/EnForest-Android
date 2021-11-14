package com.gsm.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi


class GetOverlayWindowPermission(private val mContext: Context) :
    ActivityResultContract<Void?, Boolean?>() {

    // new Intent 와 같은 역할을 하는 메소드.
    @NonNull
    override fun createIntent(@NonNull context: Context, input: Void?): Intent {
        return Intent(
         Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + context.packageName)
        )
    }

    // onActivityResult 안에서 intent 를 체크하는 것과 같은 역할을 함.
    @RequiresApi(Build.VERSION_CODES.M)
    override fun parseResult(resultCode: Int, @Nullable intent: Intent?): Boolean {
        return Settings.canDrawOverlays(mContext)
    }

}