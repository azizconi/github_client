package com.example.githubclient.feature_app.presentation.common.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun AppImage(
    url: Any?,
    modifier: Modifier
) {
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Log.e("TAG", "AppImage: $url", )
    }

    Box(
        modifier = Modifier.background(if (isLoading) Color.Gray else Color.Transparent).then(modifier),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        }
        AsyncImage(
            model = url, contentDescription = null,
            onLoading = { isLoading = true },
            onError = { isLoading = false },
            onSuccess = { isLoading = false },
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

    }
}