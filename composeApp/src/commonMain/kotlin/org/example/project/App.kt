package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kmpsteamapisample.composeapp.generated.resources.Res
import kmpsteamapisample.composeapp.generated.resources.compose_multiplatform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.example.project.dataClass.Game
import org.example.project.dataClass.Player
import org.example.project.steamApi.ApiClient
import org.example.project.steamApi.ApiClient.API_KEY
import org.example.project.steamApi.SteamRepository
import org.jetbrains.compose.resources.painterResource

@Composable
@Preview
fun App() {
    val steamId = "76561198318552263" // 例: ValveのSteamID。ご自身のIDなどに変更してください。

    // APIから取得したデータを保持する状態
    var player by remember { mutableStateOf<Player?>(null) }
    var games by remember { mutableStateOf<List<Game>>(emptyList()) }

    // API通信を行うためのリポジトリとコルーチンスコープ
    val repository = remember { SteamRepository(ApiClient.steamApi) }
    val scope = rememberCoroutineScope()


    MaterialTheme(
        darkColorScheme(
            primary = Color(0xFF171A21), // Steam風のダークカラー
            background = Color(0xFF1B2838),
            surface = Color(0xFF2A475E)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                // 1. プロフィールヘッダー部分
                ProfileHeader(
                    displayName = player?.personaname ?: "Not Found",
                    avatarUrl = player?.avatar ?: ""
                )

                HorizontalDivider(
                    Modifier.padding(vertical = 8.dp),
                    color = Color.White.copy(alpha = 0.1f)
                )

                // 2. ゲームごとのプレイ時間一覧
                GameList(games = games)


                // データを取得するためのボタン
                Button(
                    onClick = {
                        scope.launch {
                            player = repository.getProfile(API_KEY, steamId)
                            games = repository.getOwnedGames(API_KEY, steamId)
                        }
                        println("ボタンがクリックされましたplayer"+player)
                        println("ボタンがクリックされましたgames"+games)
                    },
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66C0F4))
                ) {
                    Text("プロフィール情報を取得", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(displayName: String, avatarUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO: avatarUrlを読み込んで表示する（例：coil-compose-multiplatformなど）
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text("IMG", color = Color.White)
        }

        Spacer(modifier = Modifier.width(16.dp))

        // ユーザー名
        Text(
            text = displayName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun GameList(games: List<Game>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(), // ボタンが下に来るように weight を設定
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "ライブラリ",
                color = Color(0xFF66C0F4),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(games) { game ->
            GameItemRow(game)
        }
    }
}

@Composable
fun GameItemRow(game: Game) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1B2838)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = game.name,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f) // 長い名前でレイアウトが崩れないように
            )

            Spacer(Modifier.width(16.dp))

            // プレイ時間（分→時間）
            val playTimeHours = game.playtime_forever / 60
            Text(
                text = "$playTimeHours 時間",
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }
    }
}
