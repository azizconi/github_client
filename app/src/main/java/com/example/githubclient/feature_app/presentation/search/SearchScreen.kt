package com.example.githubclient.feature_app.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubclient.core.utils.Resource
import com.example.githubclient.feature_app.presentation.common.components.RepositoryItem
import com.example.githubclient.feature_app.presentation.common.components.SearchTextField
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = koinViewModel(),
) {


    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(searchQuery.text) {
        if (searchQuery.text.isNotEmpty()) {
            viewModel.searchRepositories(searchQuery.text)
        } else viewModel.cancelSearching()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {

        SearchTextField(
            value = searchQuery,
            onValueChanged = {
                searchQuery = it
            }
        )
        when (val result = viewModel.searchResult.value) {
            is Resource.Success -> {
                if (result.data.items.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(result.data.items) { item ->

                            RepositoryItem(item = item, onDownloadClick = {}, onClick = {})
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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

            else -> {}
//            is Resource.Error -> TODO()
//            Resource.Idle -> TODO()
        }

    }

}