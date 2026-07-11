package com.allenth17.portofolio.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.allenth17.portofolio.theme.TextPrimary
import com.allenth17.portofolio.theme.TextSecondary
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import compose.icons.FeatherIcons
import compose.icons.feathericons.Github
import compose.icons.feathericons.Instagram
import compose.icons.feathericons.Linkedin
import com.allenth17.portofolio.ui.components.floatingAnimation

@Composable
fun HeroSection(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val isDesktop = maxWidth > 800.dp

        if (isDesktop) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 80.dp, vertical = 120.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeroContent(modifier = Modifier.weight(1f))
                HeroImage()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                HeroImage(modifier = Modifier.size(280.dp))
                HeroContent(modifier = Modifier.fillMaxWidth(), isMobile = true)
            }
        }
    }
}

@Composable
private fun HeroContent(modifier: Modifier = Modifier, isMobile: Boolean = false) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = if (isMobile) Alignment.CenterHorizontally else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(com.allenth17.portofolio.theme.SurfaceVariant)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Welcome to my Portfolio",
                style = MaterialTheme.typography.bodyMedium,
                color = com.allenth17.portofolio.theme.AccentPurple
            )
        }

        Text(
            text = "Hi, I'm Thiflul Ma'ani Minal Mu'min\nA Mobile Developer",
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                lineHeight = if (isMobile) 48.sp else 72.sp,
                fontSize = if (isMobile) 40.sp else 56.sp,
                textAlign = if (isMobile) androidx.compose.ui.text.style.TextAlign.Center else androidx.compose.ui.text.style.TextAlign.Start
            ),
            color = TextPrimary
        )

        Text(
            text = "A dedicated Mobile Developer and Quality Assurance specialist. I craft high-performance, seamless Android applications and ensure their reliability with robust testing strategies.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary,
            modifier = Modifier.padding(end = if (isMobile) 0.dp else 40.dp),
            textAlign = if (isMobile) androidx.compose.ui.text.style.TextAlign.Center else androidx.compose.ui.text.style.TextAlign.Start
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LottieIconButton("files/u5y6t7r8e9w0.json", "GitHub") { openUrl("https://github.com/Allenth17") }
            LottieIconButton("files/t3g4b5y6h7n8.json", "LinkedIn") { openUrl("https://linkedin.com/in/Allenth17") }
            LottieIconButton("files/e7d8c9r0f1v2.json", "Instagram") { openUrl("https://instagram.com/allenth_yzx.kt") }
            LottieIconButton("files/m9j0u1i2k3o4.json", "WhatsApp") { openUrl("https://wa.me/+6281280898820") }
        }
    }
}

@OptIn(org.jetbrains.compose.resources.ExperimentalResourceApi::class)
@Composable
private fun LottieIconButton(filePath: String, contentDescription: String, onClick: () -> Unit) {
    var jsonString by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf<String?>(null) }

    androidx.compose.runtime.LaunchedEffect(filePath) {
        try {
            val bytes = portofolio.composeapp.generated.resources.Res.readBytes(filePath)
            jsonString = bytes.decodeToString()
        } catch (e: Exception) {
            println("Failed to load Lottie icon: ${e.message}")
        }
    }

    IconButton(
        onClick = onClick,
        modifier = Modifier.background(Color(0xFFFFFFFF), CircleShape).size(48.dp)
    ) {
        if (jsonString != null) {
            val composition by io.github.alexzhirkevich.compottie.rememberLottieComposition {
                io.github.alexzhirkevich.compottie.LottieCompositionSpec.JsonString(jsonString!!)
            }
            androidx.compose.foundation.Image(
                painter = io.github.alexzhirkevich.compottie.rememberLottiePainter(
                    composition = composition,
                    iterations = io.github.alexzhirkevich.compottie.Compottie.IterateForever
                ),
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

private fun openUrl(url: String) {
    js("window.open(url, '_blank')")
}

@OptIn(org.jetbrains.compose.resources.ExperimentalResourceApi::class)
@Composable
private fun HeroImage(modifier: Modifier = Modifier.size(400.dp)) {
    var jsonString by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf<String?>(null) }

    androidx.compose.runtime.LaunchedEffect(Unit) {
        try {
            val bytes = portofolio.composeapp.generated.resources.Res.readBytes("files/a1f9d4b2e8c7.json")
            jsonString = bytes.decodeToString()
        } catch (e: Exception) {
            println("Failed to load Lottie: ${e.message}")
        }
    }

    Box(
        modifier = modifier
            .floatingAnimation(durationMillis = 4000, offsetY = 12f)
            .clip(CircleShape)
            .background(Color(0xFFFFFFFF)),
        contentAlignment = Alignment.Center
    ) {
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
                contentDescription = "Animation",
                modifier = Modifier.fillMaxSize().padding(48.dp)
            )
        }
    }
}
