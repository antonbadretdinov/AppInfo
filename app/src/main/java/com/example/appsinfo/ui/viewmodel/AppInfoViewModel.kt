package com.example.appsinfo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appsinfo.data.models.AppInfoModel
import com.example.appsinfo.data.repositories.AppInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AppInfoViewModel @Inject constructor(
    private val appInfoRepository: AppInfoRepository
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println(throwable.message)
    }

    private val mutableAppInfoStateFlow = MutableStateFlow(
        AppInfoModel(
            appName = "",
            appIcon = null,
            packageName = "",
            version = "",
            checkSum = ""
        )
    )

    val appInfoStateFlow: StateFlow<AppInfoModel> = mutableAppInfoStateFlow.asStateFlow()

    suspend fun getAppInfoByPackageName(packageName: String) {
        withContext(Dispatchers.IO + coroutineExceptionHandler) {
            mutableAppInfoStateFlow.value = appInfoRepository.getAppInfoByPackageName(packageName)
        }
    }

}