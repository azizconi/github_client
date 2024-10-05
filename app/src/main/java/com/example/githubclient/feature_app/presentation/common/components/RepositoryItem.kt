package com.example.githubclient.feature_app.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.githubclient.feature_app.data.remote.response.github_repository.GithubRepositoryResponseModel

@Composable
fun RepositoryItem(
    item: GithubRepositoryResponseModel,
    onDownloadClick: () -> Unit,
    onClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppImage(
            url = item.owner?.avatar_url,
            modifier = Modifier.size(40.dp)//.clip(CircleShape)
        )



        Column(modifier = Modifier
            .weight(1f)
            .padding(horizontal = 16.dp), verticalArrangement = Arrangement.Center) {
            Text(
                text = item.owner?.login ?: "",
                color = Color.Gray,
                fontSize = 14.sp
            )
            Text(
                text = item.name ?: "",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

        }


    }

}