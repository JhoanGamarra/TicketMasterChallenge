package com.jhoangamarral.ticketmasterchallenge.presentation.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val LocalNavControllerProvider = staticCompositionLocalOf<NavController> {
    error("You must provide a NavController before attempt to use it")
}
