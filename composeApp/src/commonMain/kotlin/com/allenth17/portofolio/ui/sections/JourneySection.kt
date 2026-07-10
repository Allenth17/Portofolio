package com.allenth17.portofolio.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.animateColorAsState
import com.allenth17.portofolio.theme.AccentIndigo
import com.allenth17.portofolio.theme.AccentPurple
import com.allenth17.portofolio.theme.GlassBackground
import com.allenth17.portofolio.theme.GlassBorder
import com.allenth17.portofolio.theme.TextPrimary
import com.allenth17.portofolio.theme.TextSecondary
import compose.icons.FeatherIcons
import compose.icons.feathericons.*
import compose.icons.feathericons.Briefcase
import compose.icons.feathericons.BookOpen
import com.allenth17.portofolio.theme.BackgroundDark


@OptIn(org.jetbrains.compose.resources.ExperimentalResourceApi::class)
@Composable
fun JourneySection(modifier: Modifier = Modifier) {
    var isWorkSelected by remember { mutableStateOf(true) }
    var jsonString by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val bytes = portofolio.composeapp.generated.resources.Res.readBytes("files/f5g6h7j8k9l0.json")
            jsonString = bytes.decodeToString()
        } catch (e: Exception) {
            println("Failed to load Lottie: ${e.message}")
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 80.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "My Journey",
                style = MaterialTheme.typography.headlineLarge,
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
                    contentDescription = "Journey Animation",
                    modifier = Modifier.size(64.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "A brief timeline of my professional & academic history",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .background(GlassBackground)
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            JourneyTab(
                title = "Experience",
                icon = FeatherIcons.Briefcase,
                isSelected = isWorkSelected,
                onClick = { isWorkSelected = true }
            )
            JourneyTab(
                title = "Education",
                icon = FeatherIcons.BookOpen,
                isSelected = !isWorkSelected,
                onClick = { isWorkSelected = false }
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier.widthIn(max = 700.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = isWorkSelected,
                transitionSpec = {
                    if (targetState) {
                        slideInHorizontally(animationSpec = tween(500)) { -it } + fadeIn() togetherWith
                        slideOutHorizontally(animationSpec = tween(500)) { it } + fadeOut()
                    } else {
                        slideInHorizontally(animationSpec = tween(500)) { it } + fadeIn() togetherWith
                        slideOutHorizontally(animationSpec = tween(500)) { -it } + fadeOut()
                    }
                }
            ) { isWork ->
                if (isWork) {
                    WorkTimeline()
                } else {
                    EducationTimeline()
                }
            }
        }
    }
}

@Composable
private fun JourneyTab(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) AccentPurple else androidx.compose.ui.graphics.Color.Transparent,
        animationSpec = tween(300)
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) BackgroundDark else TextPrimary,
        animationSpec = tween(300)
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(28.dp))
            .background(bgColor)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = contentColor
        )
    }
}

@Composable
private fun WorkTimeline() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TimelineItem(
            year = "Jul 2025 - Present",
            title = "Open to Work",
            subtitle = "Mobile Developer / QA Engineer",
            description = "Actively seeking full-time opportunities or freelance projects. Continuously refining skills in Kotlin Multiplatform, Jetpack Compose, and automated testing while building open-source projects.",
            isFirst = true
        )
        TimelineItem(
            year = "Jul 2024 - Jul 2025",
            title = "Quality Assurance Intern",
            subtitle = "PT. Cartenz Technology Indonesia",
            description = "Performed functional testing, API testing, and authored test cases for various corporate solutions. Collaborated closely with development teams to identify, document, and resolve software issues.",
            isFirst = false,
            isLast = true
        )
    }
}

@Composable
private fun EducationTimeline() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TimelineItem(
            year = "2025 - Present",
            title = "Bachelor of Computer Science",
            subtitle = "Universitas Terbuka",
            description = "Majoring in Information Systems. Balancing remote learning with practical coding, focusing on system architecture, database design, and software engineering principles.",
            isFirst = true
        )
        TimelineItem(
            year = "2022 - 2025",
            title = "Vocational High School",
            subtitle = "SMK Negeri 21 Jakarta",
            description = "Majored in Software Engineering (Rekayasa Perangkat Lunak). Gained fundamentals in mobile programming, web development, object-oriented concepts, and relational databases.",
            isFirst = false,
            isLast = false
        )
        TimelineItem(
            year = "2019 - 2022",
            title = "Junior High School",
            subtitle = "SMP Negeri 264 Jakarta",
            description = "Completed lower secondary education, participating in technology clubs and building basic digital literacy.",
            isFirst = false,
            isLast = false
        )
        TimelineItem(
            year = "2013 - 2019",
            title = "Elementary School",
            subtitle = "SD Negeri 011 Petang",
            description = "Laid the primary educational foundation, initiating a long-standing fascination with computer games and technology.",
            isFirst = false,
            isLast = true
        )
    }
}

@Composable
private fun TimelineItem(
    year: String,
    title: String,
    subtitle: String,
    description: String,
    isFirst: Boolean = false,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val glassBorderColor = GlassBorder
                val strokeWidth = 2.dp.toPx()
                val dotTop = 32.dp.toPx()
                val dotCenter = dotTop + (16.dp.toPx() / 2f)
                val xPos = 12.dp.toPx()

                if (!isFirst) {
                    drawLine(
                        color = glassBorderColor,
                        start = Offset(xPos, 0f),
                        end = Offset(xPos, dotCenter),
                        strokeWidth = strokeWidth
                    )
                }

                if (!isLast) {
                    drawLine(
                        color = glassBorderColor,
                        start = Offset(xPos, dotCenter),
                        end = Offset(xPos, size.height),
                        strokeWidth = strokeWidth
                    )
                }
            },
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Box(
            modifier = Modifier.width(24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .size(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(AccentIndigo)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(GlassBackground)
                    .padding(24.dp)
            ) {
                Text(
                    text = year,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = AccentPurple
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary
                )
            }
        }
    }
}
