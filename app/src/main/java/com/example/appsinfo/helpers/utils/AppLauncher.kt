package com.example.appsinfo.helpers.utils

import android.content.Context

fun launchAppByPackageName(context: Context, packageName: String) {
    val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
    launchIntent?.let {
        context.startActivity(launchIntent)
    }
}
