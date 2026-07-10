package com.allenth17.portofolio.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.allenth17.portofolio.theme.AccentIndigo
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
import com.allenth17.portofolio.ui.components.floatingAnimation

@OptIn(org.jetbrains.compose.resources.ExperimentalResourceApi::class)
@Composable
fun AboutSection(modifier: Modifier = Modifier) {
    var jsonString by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val bytes = portofolio.composeapp.generated.resources.Res.readBytes("files/q9p2m4n7v1c3.json")
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "About Me",
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
                        contentDescription = "About Animation",
                        modifier = Modifier
                            .size(64.dp)
                            .floatingAnimation(durationMillis = 3000, offsetY = 8f)
                    )
                }
            }

            if (isDesktop) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(64.dp)
                ) {
                    AboutBio(modifier = Modifier.weight(1f))
                    AboutJourney(modifier = Modifier.weight(1f))
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(48.dp)
                ) {
                    AboutBio(modifier = Modifier.fillMaxWidth())
                    AboutJourney(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun AboutBio(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "I am a developer who thrives at the intersection of application performance and reliability. Having graduated with a Vocational High School diploma in Software Engineering from SMKN 21 Jakarta, and currently pursuing a Bachelor of Computer Science at Universitas Terbuka, I combine solid academic foundation with practical, real-world development experience.\n\nDuring my internship at PT. Cartenz Technology Indonesia, I honed my skills in automated testing, QA methodologies, and software quality assurance, helping ship stable products. I love learning new technologies, especially Kotlin Multiplatform, Jetpack Compose, and modern automation tools.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary,
            lineHeight = 28.sp
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            StatCard(number = "4+", label = "Projects\nCompleted")
            StatCard(number = "1+", label = "Years\nExperience")
        }
    }
}

@Composable
private fun AboutJourney(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "My Journey",
            style = MaterialTheme.typography.headlineMedium,
            color = TextPrimary
        )
        JourneyItem(year = "2025 - Present", title = "Open to Work", subtitle = "Mobile Developer & QA Engineer", isLast = false)
        JourneyItem(year = "2024 - 2025", title = "Quality Assurance Intern", subtitle = "PT. Cartenz Technology Indonesia", isLast = false)
        JourneyItem(year = "2022 - 2025", title = "Software Engineering Student", subtitle = "SMK Negeri 21 Jakarta", isLast = true)
    }
}

@Composable
fun StatCard(number: String, label: String) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(GlassBackground)
            .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = number,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = AccentPurple
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun JourneyItem(year: String, title: String, subtitle: String, isLast: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .drawBehind {
                val glassBorderColor = GlassBorder
                val strokeWidth = 2.dp.toPx()
                val topPaddingPx = 8.dp.toPx()
                val dotCenterY = topPaddingPx + (16.dp.toPx() / 2f)
                val xPos = 8.dp.toPx()

                if (!isLast) {
                    drawLine(
                        color = glassBorderColor,
                        start = Offset(xPos, dotCenterY),
                        end = Offset(xPos, size.height + 24.dp.toPx()),
                        strokeWidth = strokeWidth
                    )
                }
            },
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Box(
            modifier = Modifier.width(16.dp).fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Brush.linearGradient(listOf(AccentPurple, AccentIndigo)))
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = year,
                style = MaterialTheme.typography.labelLarge,
                color = AccentIndigo
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
