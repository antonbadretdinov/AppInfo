package com.example.appsinfo.data.models

import android.graphics.drawable.Drawable

data class AppInfoModel(
    val appName: String,
    val appIcon: Drawable?,
    val packageName: String,
    val version: String,
    val checkSum: String
)