package com.allenth17.portofolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.allenth17.portofolio.theme.AccentIndigo
import com.allenth17.portofolio.theme.AccentPurple
import com.allenth17.portofolio.theme.GlassBackground
import com.allenth17.portofolio.theme.GlassBorder
import com.allenth17.portofolio.theme.TextPrimary
import com.allenth17.portofolio.theme.TextSecondary
import com.allenth17.portofolio.theme.TextSecondary

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import com.allenth17.portofolio.ui.components.hoverScale

@OptIn(org.jetbrains.compose.resources.ExperimentalResourceApi::class)
@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit = {}
) {
    var jsonString by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val bytes = portofolio.composeapp.generated.resources.Res.readBytes("files/a1f9d4b2e8c7.json")
            jsonString = bytes.decodeToString()
        } catch (e: Exception) {
            println("Failed to load Cat Lottie: ${e.message}")
        }
    }
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(GlassBackground)
            .border(1.dp, GlassBorder)
            .padding(horizontal = 16.dp)
    ) {
        val isDesktop = maxWidth > 800.dp

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                        contentDescription = "Cat Hacker",
                        modifier = Modifier.size(40.dp)
                    )
                }

                Text(
                    text = "Allenth",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = if (isDesktop) 20.sp else 18.sp,
                        letterSpacing = 1.sp
                    ),
                    color = TextPrimary
                )
            }

            if (isDesktop) { Spacer(modifier = Modifier.width(24.dp)) }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = if (isDesktop) Arrangement.Center else Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val navItems = listOf("Home", "About", "Journey", "Portfolio", "Contact")

                val scrollIndices = listOf(1, 2, 3, 4, 8)

                Row(horizontalArrangement = Arrangement.spacedBy(if (isDesktop) 24.dp else 16.dp)) {
                    navItems.forEachIndexed { index, item ->
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = if (isDesktop) 16.sp else 14.sp
                            ),
                            color = if (item == "Home") TextPrimary else TextSecondary,
                            modifier = Modifier.hoverScale(1.1f).clickable { onNavigate(scrollIndices[index]) }
                        )
                    }
                }
            }
        }
    }
}
