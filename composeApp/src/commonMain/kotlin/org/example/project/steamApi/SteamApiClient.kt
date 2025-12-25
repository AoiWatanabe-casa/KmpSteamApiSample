package org.example.project.steamApi

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {
    // KtorのHttpClientの設定（あとでJSONパースを自動化するために必要）
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // APIから余計なデータが来てもエラーにしない
                isLenient = true
            })
        }
    }

    // Ktorfitインスタンスの保持
    private val ktorfit = Ktorfit.Builder()
        .baseUrl("https://api.steampowered.com/")
        .httpClient(httpClient)
        .build()

    // SteamApiの実装を一度だけ生成して公開
    val steamApi: SteamApi = ktorfit.createSteamApi()

    // 自分のアカウントで作成したAPIKEYを使う
    val API_KEY = "XXXXXXXXXXXXXXXXXXXXXXXXXXXX"
}