package com.allenth17.portofolio.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.allenth17.portofolio.theme.*
import com.allenth17.portofolio.ui.components.hoverScale
import com.allenth17.portofolio.theme.BackgroundDark


@JsName("_oP")
private external fun openPdfViewer(url: String)

@OptIn(org.jetbrains.compose.resources.ExperimentalResourceApi::class)
@Composable
fun CertificateSection(modifier: Modifier = Modifier) {
    var jsonString by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf<String?>(null) }

    androidx.compose.runtime.LaunchedEffect(Unit) {
        try {
            val bytes = portofolio.composeapp.generated.resources.Res.readBytes("files/z8k5j4l2h1g9.json")
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
                text = "Certifications",
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
                    contentDescription = "Certificate Animation",
                    modifier = Modifier.size(64.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Professional achievements and verified credentials",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(48.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth().widthIn(max = 1000.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            CertificateCard(
                title = "Quality Assurance Internship",
                org = "PT. Cartenz Technology Indonesia",
                year = "2025",
                pdfUrl = "pdf/Sertifikat%20Internship_Thiflul%20Ma'ani%20Minal%20Mu'min.pdf"
            )
            CertificateCard(
                title = "Junior Mobile Programmer (BNSP)",
                org = "Badan Nasional Sertifikasi Profesi",
                year = "2025",
                pdfUrl = "pdf/BNSP%20-%20Junior%20Mobile%20Programmer.%20Thiflul%20Ma'ani%20Minal%20Mu'min.pdf"
            )
        }
    }
}

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@Composable
private fun CertificateCard(title: String, org: String, year: String, pdfUrl: String) {
    Row(
        modifier = Modifier
            .width(340.dp)
            .hoverScale(1.02f)
            .clip(RoundedCornerShape(16.dp))
            .background(GlassBackground)
            .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
            .clickable { openPdfViewer(pdfUrl) }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(androidx.compose.ui.graphics.Brush.linearGradient(listOf(AccentIndigo, AccentPurple))),
            contentAlignment = Alignment.Center
        ) {
            Text(org.take(1), style = MaterialTheme.typography.titleLarge.copy(color = BackgroundDark, fontWeight = FontWeight.Bold))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$org • $year",
                style = MaterialTheme.typography.bodyMedium,
                color = AccentIndigo
            )
        }
    }
}
