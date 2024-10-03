package com.example.githubclient.feature_app.presentation.common.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable data object Search

}