package com.jhoangamarral.ticketmasterchallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jhoangamarral.ticketmasterchallenge.presentation.navigation.LocalNavControllerProvider
import com.jhoangamarral.ticketmasterchallenge.presentation.navigation.Page
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.base.LocalViewModelStore
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.base.ViewModelStore
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.feed.feedScreen
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.search.searchScreen
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModelStore = remember { ViewModelStore() }

            AppTheme() {
                CompositionLocalProvider(
                    LocalNavControllerProvider provides navController,
                    LocalViewModelStore provides viewModelStore
                ){
                    NavHost(navController = navController, startDestination = Page.Feed.route ){
                        feedScreen()
                        searchScreen()
                    }
                }
            }
        }
    }
}
