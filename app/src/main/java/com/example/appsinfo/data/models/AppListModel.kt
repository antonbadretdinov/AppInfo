package com.example.appsinfo.data.models

import android.graphics.drawable.Drawable

data class AppListModel(
    val appName: String,
    val appIcon: Drawable,
    val packageName: String,
)