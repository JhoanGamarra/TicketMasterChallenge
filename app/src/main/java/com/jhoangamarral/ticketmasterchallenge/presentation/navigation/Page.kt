package com.jhoangamarral.ticketmasterchallenge.presentation.navigation

sealed class Page(val route: String) {
    object Feed : Page("feed")
    object Search : Page("search")
}
