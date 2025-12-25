package org.example.project.dataClass

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GamesResponse(val response: GamesListResponse)

@Serializable
data class GamesListResponse(val game_count: Int, val games: List<Game>)

@Serializable
data class Game(val appid: Int, val name: String, val playtime_forever: Int, val img_icon_url: String)

@Serializable
data class PlayerSummariesResponse(val response: PlayerSummariesListResponse)

@Serializable
data class PlayerSummariesListResponse(val players: List<Player>)

@Serializable
data class Player(
    @SerialName("steamid")
    val steamId: String,
    val personaname: String,
    val profileurl: String,
    val avatar: String,
    val avatarfull: String
)
