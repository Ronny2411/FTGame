package com.ronny.gamemingle.presentation.navigation

sealed class Route(val route: String) {
    object HomeScreen : Route(route = "homescreen")
    object DetailsScreen: Route(route = "detailscreen/{id}"){
        fun createRoute(id: String?) = "detailscreen/$id"
    }
}