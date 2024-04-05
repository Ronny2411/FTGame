package com.ronny.gamemingle.domain.model

data class Game(
    val description: String? = null,
    val developer: String? = null,
    val freetogame_profile_url: String? = null,
    val game_url: String? = null,
    val genre: String? = null,
    val id: Int? = null,
    val minimum_system_requirements: MinimumSystemRequirements? = null,
    val platform: String? = null,
    val publisher: String? = null,
    val release_date: String? = null,
    val screenshots: List<Screenshot?>? = null,
    val short_description: String? = null,
    val status: String? = null,
    val thumbnail: String? = null,
    val title: String? = null
)