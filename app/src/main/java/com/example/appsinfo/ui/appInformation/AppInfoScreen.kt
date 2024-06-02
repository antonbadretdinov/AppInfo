package com.example.appsinfo.ui.appInformation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.appsinfo.R
import com.example.appsinfo.helpers.utils.launchAppByPackageName
import com.example.appsinfo.ui.viewmodel.AppInfoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInformationScreen(
    packageName: String,
    appInfoViewModel: AppInfoViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onBackClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val appInfoUIState = appInfoViewModel.appInfoStateFlow.collectAsStateWithLifecycle()

    var orientation by remember { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }

    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                scope.launch(Dispatchers.IO) {
                    appInfoViewModel.getAppInfoByPackageName(packageName)
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text(
                        stringResource(R.string.information)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = stringResource(
                                R.string.back_button_icon
                            )
                        )
                    }
                }
            )
        }) { appBarPadding ->
        if (appInfoUIState.value.checkSum.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(appBarPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Column(
                    modifier = Modifier
                        .padding(appBarPadding)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(appInfoUIState.value.appIcon),
                        contentDescription = stringResource(id = R.string.icon_for_app),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(0.3f)
                            .aspectRatio(1f)
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        TextInfoItem(
                            title = stringResource(R.string.appNameInfo),
                            info = appInfoUIState.value.appName
                        )

                        TextInfoItem(
                            title = stringResource(R.string.packageNameInfo),
                            info = appInfoUIState.value.packageName
                        )

                        TextInfoItem(
                            title = stringResource(R.string.checkSumInfo),
                            info = appInfoUIState.value.checkSum
                        )

                        TextInfoItem(
                            title = stringResource(R.string.versionInfo),
                            info = appInfoUIState.value.version
                        )
                    }

                    ExtendedFloatingActionButton(
                        modifier = Modifier.padding(24.dp),
                        onClick = {
                            launchAppByPackageName(
                                context = context,
                                packageName = appInfoUIState.value.packageName
                            )
                        }) {

                        Text(
                            text = "${stringResource(R.string.openButton)} ${appInfoUIState.value.appName}"
                        )
                    }
                }

            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(appBarPadding)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(appInfoUIState.value.appIcon),
                            contentDescription = stringResource(id = R.string.icon_for_app),
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxHeight(0.4f)
                                .aspectRatio(1f)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextInfoItem(
                                title = stringResource(R.string.appNameInfo),
                                info = appInfoUIState.value.appName
                            )

                            TextInfoItem(
                                title = stringResource(R.string.packageNameInfo),
                                info = appInfoUIState.value.packageName
                            )

                            TextInfoItem(
                                title = stringResource(R.string.checkSumInfo),
                                info = appInfoUIState.value.checkSum
                            )

                            TextInfoItem(
                                title = stringResource(R.string.versionInfo),
                                info = appInfoUIState.value.version
                            )

                            ExtendedFloatingActionButton(
                                modifier = Modifier.padding(16.dp),
                                onClick = {
                                    launchAppByPackageName(
                                        context = context,
                                        packageName = appInfoUIState.value.packageName
                                    )
                                }) {

                                Text(
                                    text = "${stringResource(R.string.openButton)} ${appInfoUIState.value.appName}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}