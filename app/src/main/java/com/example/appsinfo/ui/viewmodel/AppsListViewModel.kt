package com.example.appsinfo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsinfo.data.models.AppListModel
import com.example.appsinfo.data.repositories.AppsListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsListViewModel @Inject constructor(
    private val repository: AppsListRepository
) : ViewModel() {

    private val mutableAppsListStateFlow = MutableStateFlow(emptyList<AppListModel>())

    val appsListStateFlow: StateFlow<List<AppListModel>> = mutableAppsListStateFlow.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println(throwable.message)
    }

    suspend fun getAppsList() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            mutableAppsListStateFlow.value = repository.getAppsList()
        }
    }

}