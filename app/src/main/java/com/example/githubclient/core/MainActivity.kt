package com.example.githubclient.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubclient.core.theme.GithubClientTheme
import com.example.githubclient.feature_app.domain.use_case.DownloadRepositoryUseCase
import com.example.githubclient.feature_app.domain.use_case.LocalDownloadUseCase
import com.example.githubclient.feature_app.presentation.common.navigation.AppNavigation
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val localDownloadUseCase: LocalDownloadUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubClientTheme {
                LaunchedEffect(Unit) {
                    localDownloadUseCase.cleanTableOnAppCreate()
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}


