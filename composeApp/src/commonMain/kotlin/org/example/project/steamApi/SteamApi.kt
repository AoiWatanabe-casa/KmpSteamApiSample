package org.example.project.steamApi

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import org.example.project.dataClass.GamesResponse
import org.example.project.dataClass.PlayerSummariesResponse

interface SteamApi {
    @GET("IPlayerService/GetOwnedGames/v0001/")
    suspend fun getOwnedGames(
        @Query("key") apiKey: String,
        @Query("steamid") steamId: String,
        @Query("format") format: String = "json",
        @Query("include_appinfo") includeAppInfo: Boolean = true
    ): GamesResponse

    @GET("ISteamUser/GetPlayerSummaries/v0002/")
    suspend fun getPlayerSummaries(
        @Query("key") apiKey: String,
        @Query("steamids") steamId: String,
        @Query("format") format: String = "json"
    ): PlayerSummariesResponse
}
