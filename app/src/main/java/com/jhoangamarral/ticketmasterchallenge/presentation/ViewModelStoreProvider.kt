package com.jhoangamarral.ticketmasterchallenge.presentation

import androidx.compose.runtime.staticCompositionLocalOf

val LocalViewModelStore = staticCompositionLocalOf<ViewModelStore> {
    error("ViewModel must be provided first")
}
