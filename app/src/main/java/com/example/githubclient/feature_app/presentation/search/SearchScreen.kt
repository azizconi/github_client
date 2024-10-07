package com.example.githubclient.feature_app.presentation.search

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.githubclient.R
import com.example.githubclient.core.utils.Resource
import com.example.githubclient.feature_app.presentation.common.components.RepositoryItem
import com.example.githubclient.feature_app.presentation.common.components.RepositoryLoadState
import com.example.githubclient.feature_app.presentation.common.components.SearchTextField
import com.example.githubclient.feature_app.presentation.common.navigation.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.util.Date


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = koinViewModel(),
    navController: NavHostController
) {

    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val allSearchesHistory = viewModel.allSearchesHistory.collectAsState(emptyList())

    val allDownloadingRepositories by viewModel.allDownloadingRepositories.collectAsState(emptyList())

    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        null
    }


    fun callPermission(
        onPermissionsGranted: () -> Unit
    ) {
        when {
            notificationPermissionState != null && !notificationPermissionState.status.isGranted -> {
                notificationPermissionState.launchPermissionRequest()
            }
            else -> {
                onPermissionsGranted()
            }
        }
    }

    LaunchedEffect(searchQuery.text) {
        if (searchQuery.text.isNotEmpty()) {
            viewModel.searchRepositories(searchQuery.text)
        } else viewModel.cancelSearching()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 8.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            SearchTextField(
                value = searchQuery,
                onValueChanged = {
                    searchQuery = it
                }
            )

            IconButton(onClick = {
                navController.navigate(Screen.Downloads)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_cloud_download_24),
                    contentDescription = null
                )
            }
        }
        if (searchQuery.text.isNotEmpty()) {
            when (val result = viewModel.searchResult.value) {
                is Resource.Success -> {
                    if (result.data.items.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(
                                items = result.data.items,
                                key = { it.id }
                            ) { item ->

                                val downloadEntity = allDownloadingRepositories.find {
                                    it.id == item.id
                                }
                                val downloadState = when {
                                    downloadEntity == null -> {
                                        RepositoryLoadState.IDLE
                                    }

                                    downloadEntity.isLoading -> {
                                        RepositoryLoadState.DOWNLOADING
                                    }

                                    else -> {
                                        RepositoryLoadState.DOWNLOADED
                                    }
                                }

                                RepositoryItem(
                                    item = item, onDownloadClick = {
                                        callPermission {
                                            val downloadsDir =
                                                Environment.getExternalStoragePublicDirectory(
                                                    Environment.DIRECTORY_DOWNLOADS
                                                )

                                            val outputFile = File(downloadsDir, "${item.name}.zip")
                                            if (downloadState == RepositoryLoadState.IDLE) {
                                                viewModel.startDownload(item, outputFile)
                                            }
                                        }

                                    },
                                    onClick = {
                                        viewModel.saveSearchHistory(item.toSearchHistoryModel())

                                        Intent(
                                            Intent.ACTION_VIEW, Uri.parse(item.html_url)
                                        ).also {
                                            context.startActivity(it)
                                        }
                                    },
                                    repositoryLoadState = downloadState
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "По вашему запросу не найдены репозитории",
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        }
                    }
                }

                Resource.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is Resource.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Ошибка при поиске",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    }
                }

                else -> {}
//            Resource.Idle -> TODO()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(
                    items = allSearchesHistory.value.sortedByDescending { Date(it.updated) },
                    key = { it.id }
                ) { item ->

                    val downloadEntity = allDownloadingRepositories.find {
                        it.id == item.id
                    }
                    val downloadState = when {
                        downloadEntity == null -> {
                            RepositoryLoadState.IDLE
                        }

                        downloadEntity.isLoading -> {
                            RepositoryLoadState.DOWNLOADING
                        }

                        else -> {
                            RepositoryLoadState.DOWNLOADED
                        }
                    }

                    RepositoryItem(
                        item = item.toResponseModel(), onDownloadClick = {
                            callPermission {
                                val downloadsDir =
                                    Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_DOWNLOADS
                                    )

                                val outputFile = File(downloadsDir, "${item.name}.zip")
                                if (downloadState == RepositoryLoadState.IDLE) {
                                    viewModel.startDownload(item.toResponseModel(), outputFile)
                                }
                            }

                        },
                        onClick = {
                            viewModel.saveSearchHistory(item.copy(updated = Date().time))
                            Intent(
                                Intent.ACTION_VIEW, Uri.parse(item.html_url)
                            ).also {
                                context.startActivity(it)
                            }
                        },
                        repositoryLoadState = downloadState
                    )
                }
            }
        }

    }

}

