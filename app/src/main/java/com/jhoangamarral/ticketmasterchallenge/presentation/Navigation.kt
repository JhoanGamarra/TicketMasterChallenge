package com.jhoangamarral.ticketmasterchallenge.presentation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val LocalNavControllerProvider = staticCompositionLocalOf<NavController> {
    error("You must provide a NavController before attempt to use it")
}
