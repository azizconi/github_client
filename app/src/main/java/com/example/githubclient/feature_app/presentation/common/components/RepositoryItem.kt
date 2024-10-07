package com.example.githubclient.feature_app.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubclient.R
import com.example.githubclient.feature_app.data.remote.response.github_repository.GithubRepositoryResponseModel
import com.example.githubclient.feature_app.domain.entity.download.DownloadEntity

@Composable
fun RepositoryItem(
    item: GithubRepositoryResponseModel,
    repositoryLoadState: RepositoryLoadState,
    onDownloadClick: () -> Unit,
    onClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppImage(
            url = item.owner?.avatar_url,
            modifier = Modifier.size(40.dp)//.clip(CircleShape)
        )



        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp), verticalArrangement = Arrangement.Center
        ) {
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

        when (repositoryLoadState) {
            RepositoryLoadState.IDLE -> {
                IconButton(onClick = onDownloadClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_downloading_24),
                        contentDescription = null
                    )
                }
            }

            RepositoryLoadState.DOWNLOADING -> {
                IconButton(onClick = {}) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            RepositoryLoadState.DOWNLOADED -> {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_file_download_done_24),
                        contentDescription = null
                    )
                }
            }
        }


    }

}


@Composable
fun RepositoryDownloadItem(
    item: DownloadEntity,
    repositoryLoadState: RepositoryLoadState,
    onDownloadClick: () -> Unit,
    onClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppImage(
            url = item.owner?.avatar_url,
            modifier = Modifier.size(40.dp)//.clip(CircleShape)
        )



        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp), verticalArrangement = Arrangement.Center
        ) {
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

        when (repositoryLoadState) {
            RepositoryLoadState.IDLE -> {
                IconButton(onClick = onDownloadClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_downloading_24),
                        contentDescription = null
                    )
                }
            }

            RepositoryLoadState.DOWNLOADING -> {
                IconButton(onClick = {}) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            RepositoryLoadState.DOWNLOADED -> {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_file_download_done_24),
                        contentDescription = null
                    )
                }
            }
        }


    }

}



enum class RepositoryLoadState {
    IDLE, DOWNLOADING, DOWNLOADED
}