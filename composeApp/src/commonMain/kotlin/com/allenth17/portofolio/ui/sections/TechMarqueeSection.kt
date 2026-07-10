package com.allenth17.portofolio.ui.sections

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.allenth17.portofolio.theme.*

val TechStackList = listOf(
    "Kotlin" to "Multiplatform",
    "Jetpack Compose" to "UI Framework",
    "Android" to "Mobile OS",
    "Playwright" to "Automation Tests",
    "PostgreSQL" to "Database",
    "Postman" to "API Testing",
    "Git" to "Version Control",
    "React" to "Frontend Lib",
    "Golang" to "Backend",
    "TypeScript" to "Web Logic",
    "Elixir" to "Backend"
)

@OptIn(org.jetbrains.compose.resources.ExperimentalResourceApi::class)
@Composable
fun TechMarqueeSection(modifier: Modifier = Modifier) {
    var jsonString by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val bytes = portofolio.composeapp.generated.resources.Res.readBytes("files/d9s0a1p2o3i4.json")
            jsonString = bytes.decodeToString()
        } catch (e: Exception) {
            println("Failed to load Lottie: ${e.message}")
        }
    }

    val density = LocalDensity.current
    val itemWidthPx = with(density) { 260.dp.toPx() }
    val spacingPx = with(density) { 24.dp.toPx() }
    val cycleWidthPx = (itemWidthPx + spacingPx) * TechStackList.size

    val infiniteTransition = rememberInfiniteTransition(label = "marquee")
    val autoScrollRatio by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 30000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "marquee_ratio"
    )

    var dragOffset by remember { mutableStateOf(0f) }
    val draggableState = rememberDraggableState { delta ->
        dragOffset += delta
    }

    val totalOffset = (autoScrollRatio * -cycleWidthPx) + dragOffset
    var wrappedOffset = totalOffset % cycleWidthPx
    if (wrappedOffset > 0) {
        wrappedOffset -= cycleWidthPx
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Technologies & Tools",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )

            val currentJson = jsonString
            if (currentJson != null) {
                val composition by io.github.alexzhirkevich.compottie.rememberLottieComposition {
                    io.github.alexzhirkevich.compottie.LottieCompositionSpec.JsonString(currentJson)
                }

                androidx.compose.foundation.Image(
                    painter = io.github.alexzhirkevich.compottie.rememberLottiePainter(
                        composition = composition,
                        iterations = io.github.alexzhirkevich.compottie.Compottie.IterateForever
                    ),
                    contentDescription = "Tech Animation",
                    modifier = Modifier.size(64.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(64.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(0.dp))
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Horizontal
                )
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth(unbounded = true, align = Alignment.Start)
                    .graphicsLayer {
                        translationX = wrappedOffset
                    },
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                val infiniteStack = TechStackList + TechStackList + TechStackList + TechStackList + TechStackList + TechStackList + TechStackList + TechStackList + TechStackList + TechStackList
                infiniteStack.forEach { pair ->
                    TechMarqueeCard(title = pair.first, category = pair.second)
                }
            }
        }
    }
}

@Composable
private fun TechMarqueeCard(title: String, category: String) {
    Row(
        modifier = Modifier
            .width(260.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(GlassBackground)
            .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(AccentPurple.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = title.take(1),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = AccentIndigo
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(verticalArrangement = Arrangement.Center) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
            Text(
                category,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
    }
}
