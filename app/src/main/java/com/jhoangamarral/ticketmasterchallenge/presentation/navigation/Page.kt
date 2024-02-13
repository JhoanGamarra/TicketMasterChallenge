package com.jhoangamarral.ticketmasterchallenge.presentation.navigation

sealed class Page(val route: String) {
    object Feed : Page("feed")
    object Search : Page("search")
}

enum class Graphs {
    GRAPH_ROUTE_MAIN,
}