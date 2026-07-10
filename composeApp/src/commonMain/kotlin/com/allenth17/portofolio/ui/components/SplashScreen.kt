package com.allenth17.portofolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.allenth17.portofolio.theme.*
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter

@OptIn(org.jetbrains.compose.resources.ExperimentalResourceApi::class)
@Composable
fun SplashScreen(modifier: Modifier = Modifier, onSplashComplete: () -> Unit) {
    var progress by remember { mutableStateOf(0) }
    var jsonString by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val bytes = portofolio.composeapp.generated.resources.Res.readBytes("files/l3k4j5h6g7f8.json")
            jsonString = bytes.decodeToString()
        } catch (e: Exception) {
            println("Failed to load Splash Lottie: ${e.message}")
        }

        val totalDuration = 2500L
        val interval = 25L
        val increments = (totalDuration / interval).toInt()

        for (i in 1..increments) {
            delay(interval)
            progress = (i.toFloat() / increments * 100).toInt()
        }
        progress = 100
        delay(400)
        onSplashComplete()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (jsonString != null) {
                val composition by rememberLottieComposition {
                    LottieCompositionSpec.JsonString(jsonString!!)
                }
                androidx.compose.foundation.Image(
                    painter = rememberLottiePainter(
                        composition = composition,
                        iterations = Compottie.IterateForever
                    ),
                    contentDescription = "Loading Animation",
                    modifier = Modifier.size(120.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(120.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Allenth's \u00A9 Portfolio",
                style = MaterialTheme.typography.titleMedium.copy(color = TextSecondary, fontWeight = FontWeight.Medium)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Initialize $progress%",
                style = MaterialTheme.typography.bodyMedium.copy(color = AccentIndigo, fontWeight = FontWeight.Bold)
            )
        }
    }
}
