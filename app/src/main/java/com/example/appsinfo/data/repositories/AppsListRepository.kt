package com.example.appsinfo.data.repositories

import android.content.Context
import android.content.Intent
import com.example.appsinfo.data.models.AppListModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppsListRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun getAppsList(): List<AppListModel> = withContext(Dispatchers.IO) {

        val listOfApps = mutableListOf<AppListModel>()

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val packageManager = context.packageManager
        val queryIntentActivities = packageManager.queryIntentActivities(mainIntent, 0)

        for (app in queryIntentActivities) {
            if (app.activityInfo != null) {
                val res =
                    packageManager.getResourcesForApplication(app.activityInfo.applicationInfo)

                val name = if (app.activityInfo.labelRes != 0) {
                    res.getString(app.activityInfo.labelRes)
                } else {
                    app.activityInfo.applicationInfo.loadLabel(packageManager).toString()
                }
                val packageName = app.activityInfo.applicationInfo.packageName.toString()
                val appIcon =
                    packageManager.getApplicationInfo(packageName, 0).loadIcon(packageManager)

                listOfApps.add(
                    AppListModel(
                        appName = name,
                        packageName = packageName,
                        appIcon = appIcon
                    )
                )
            }
        }
        return@withContext listOfApps
    }
}