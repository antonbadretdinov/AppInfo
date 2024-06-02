package com.example.appsinfo.data.repositories

import android.content.Context
import com.example.appsinfo.data.models.AppInfoModel
import com.example.appsinfo.helpers.SHA_256_ALGORITHM
import com.example.appsinfo.helpers.utils.HashUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import javax.inject.Inject

class AppInfoRepository @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun getAppInfoByPackageName(packageName: String): AppInfoModel =
        withContext(Dispatchers.IO) {
            val packageManager = context.packageManager

            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)

            val appName = packageManager.getApplicationLabel(applicationInfo).toString()
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val versionName = packageInfo.versionName
            val appIcon = applicationInfo.loadIcon(packageManager)

            val apkFilePath = packageInfo.applicationInfo.sourceDir

            val checkSum = HashUtils.getCheckSumFromFile(
                digest = MessageDigest.getInstance(SHA_256_ALGORITHM),
                filePath = apkFilePath
            )

            return@withContext AppInfoModel(
                appName = appName,
                appIcon = appIcon,
                packageName = packageName,
                version = versionName,
                checkSum = checkSum
            )
        }
}