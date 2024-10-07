package com.example.githubclient.feature_app.presentation.downloads

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.example.githubclient.feature_app.presentation.common.components.RepositoryDownloadItem
import com.example.githubclient.feature_app.presentation.common.components.RepositoryItem
import com.example.githubclient.feature_app.presentation.common.components.RepositoryLoadState
import org.koin.androidx.compose.koinViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadsScreen(
    navController: NavHostController,
    viewModel: DownloadsScreenViewModel = koinViewModel(),
) {

    val allDownloads by viewModel.allDownloads.collectAsState(emptyList())
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Загрузки")
                },
                navigationIcon = {
                    IconButton(onClick = navController::navigateUp) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {

        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = it.calculateTopPadding()
            )
        ) {
            items(allDownloads) { item ->
                val downloadState = when {
                    item.isLoading -> {
                        RepositoryLoadState.DOWNLOADING
                    }

                    else -> {
                        RepositoryLoadState.DOWNLOADED
                    }
                }

                fun openZIP() {
                    if (downloadState == RepositoryLoadState.DOWNLOADED) {
                        val downloadsDir =
                            Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS
                            )

                        val outputFile = File(downloadsDir, "${item.name}.zip")
                        openZipFileWithExternalApp(context = context, file = outputFile)
                    }
                }
                RepositoryDownloadItem(
                    item = item,
                    onDownloadClick = {
                        openZIP()
                    },
                    onClick = {
                        openZIP()
                    },
                    repositoryLoadState = downloadState
                )

            }
        }
    }


}

fun openZipFileWithExternalApp(context: Context, file: File) {
    try {
        // Получаем content URI через FileProvider
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        // Создаем Intent для открытия файла
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/zip")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Разрешаем доступ к URI

        // Запускаем активность
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        // Обработка ошибки
    }
}