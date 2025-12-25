package org.example.project.steamApi

import org.example.project.dataClass.Game
import org.example.project.dataClass.Player

class SteamRepository(private val api: SteamApi) {
    suspend fun getProfile(apiKey: String, steamId: String): Player? {
        return try {
            val response = api.getPlayerSummaries(apiKey, steamId)
            // JSONから必要なPlayer情報だけを抜き出して返す
            response.response.players.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getOwnedGames(apiKey: String, steamId: String): List<Game> {
        return try {
            val response = api.getOwnedGames(apiKey, steamId)
            response.response.games
        }catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

}