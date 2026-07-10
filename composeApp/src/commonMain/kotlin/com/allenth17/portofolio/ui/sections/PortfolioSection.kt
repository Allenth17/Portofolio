@file:OptIn(ExperimentalLayoutApi::class)

package com.allenth17.portofolio.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.allenth17.portofolio.theme.AccentPurple
import com.allenth17.portofolio.theme.GlassBackground
import com.allenth17.portofolio.theme.GlassBorder
import com.allenth17.portofolio.theme.TextPrimary
import com.allenth17.portofolio.theme.TextSecondary
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import com.allenth17.portofolio.ui.components.hoverScale

@OptIn(org.jetbrains.compose.resources.ExperimentalResourceApi::class)
@Composable
fun PortfolioSection(modifier: Modifier = Modifier) {
    var jsonString by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val bytes = portofolio.composeapp.generated.resources.Res.readBytes("files/z1x2c3v4b5n6.json")
            jsonString = bytes.decodeToString()
        } catch (e: Exception) {
            println("Failed to load Lottie: ${e.message}")
        }
    }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val isDesktop = maxWidth > 800.dp

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = if (isDesktop) 80.dp else 24.dp, vertical = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Recent Projects",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
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
                        contentDescription = "Portfolio Animation",
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Take a look at some of my recent work",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary
                )
            }

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                maxItemsInEachRow = 3
            ) {
                ProjectCard(
                    title = "Mirikanime",
                    description = "A fast and responsive multiplatform anime streaming application.",
                    repoUrl = "https://github.com/Polyvor-Labs/Mirikanime"
                )
                ProjectCard(
                    title = "Blaster Browser",
                    description = "Customized browser with ad-blocking and privacy features.",
                    repoUrl = "https://github.com/Allenth17/Blaster"
                )
                ProjectCard(
                    title = "Qalbun",
                    description = "Islamic lifestyle app featuring Quran, Asmaul Husna, and Prayers.",
                    repoUrl = "https://github.com/Polyvor-Labs/Qalbun"
                )
                ProjectCard(
                    title = "TemuBelajar",
                    description = "A platform for students to find friends, from a different campus",
                    repoUrl = "https://github.com/Allenth17/TemuBelajar"
                )
            }
        }
    }
}

@Composable
fun ProjectCard(title: String, description: String, repoUrl: String) {
    val imageUrl = "https://opengraph.githubassets.com/1/" + repoUrl.replace("https://github.com/", "").replace("http://github.com/", "")

    Column(
        modifier = Modifier
            .width(320.dp)
            .hoverScale(1.02f)
            .clip(RoundedCornerShape(16.dp))
            .background(GlassBackground)
            .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
            .clickable { openUrl(repoUrl) }
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color(0xFFE5E7EB)),
            contentAlignment = Alignment.Center
        ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "$title Thumbnail",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
        }

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            Text(
                text = "View Project >",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = AccentPurple,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

private fun openUrl(url: String) {
    js("window.open(url, '_blank')")
}
