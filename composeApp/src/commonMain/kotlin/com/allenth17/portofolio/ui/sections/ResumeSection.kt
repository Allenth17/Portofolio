package com.allenth17.portofolio.ui.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.sp
import com.allenth17.portofolio.theme.*
import com.allenth17.portofolio.ui.components.hoverScale
import compose.icons.FeatherIcons
import compose.icons.feathericons.*

@JsName("_oP")
private external fun openPdfViewer(url: String)

@JsName("_dF")
private external fun downloadFile(url: String, filename: String)

data class ResumeItem(
    val title: String,
    val subtitle: String,
    val type: String,
    val size: String,
    val pdfUrl: String,
    val filename: String,
    val category: String, // "Mobile", "QA", "Universal"
    val language: String  // "EN", "ID"
)

@OptIn(ExperimentalLayoutApi::class, org.jetbrains.compose.resources.ExperimentalResourceApi::class)
@Composable
fun ResumeSection(modifier: Modifier = Modifier) {
    var jsonString by remember { mutableStateOf<String?>(null) }
    var selectedLanguage by remember { mutableStateOf("ALL") } // "ALL", "EN", "ID"
    var selectedCategory by remember { mutableStateOf("ALL") } // "ALL", "Mobile", "QA", "Universal"

    LaunchedEffect(Unit) {
        try {
            val bytes = portofolio.composeapp.generated.resources.Res.readBytes("files/m7n8b9v0c1x2.json")
            jsonString = bytes.decodeToString()
        } catch (e: Exception) {
            println("Failed to load Lottie: ${e.message}")
        }
    }

    val resumes = remember {
        listOf(
            ResumeItem("Mobile Developer CV", "Professional Profile", "PDF", "172 KB", "pdf/CV_MobileDeveloper_EN.pdf", "CV_MobileDeveloper_EN.pdf", "Mobile", "EN"),
            ResumeItem("Quality Assurance CV", "Professional Profile", "PDF", "166 KB", "pdf/CV_QA_EN.pdf", "CV_QA_EN.pdf", "QA", "EN"),
            ResumeItem("Universal CV", "Professional Profile", "PDF", "166 KB", "pdf/CV_Universal_EN.pdf", "CV_Universal_EN.pdf", "Universal", "EN"),
            ResumeItem("Mobile Developer Resume", "Brief Resume", "PDF", "159 KB", "pdf/Resume_MobileDeveloper_EN.pdf", "Resume_MobileDeveloper_EN.pdf", "Mobile", "EN"),
            ResumeItem("Quality Assurance Resume", "Brief Resume", "PDF", "159 KB", "pdf/Resume_QA_EN.pdf", "Resume_QA_EN.pdf", "QA", "EN"),
            ResumeItem("Universal Resume", "Brief Resume", "PDF", "158 KB", "pdf/Resume_Universal_EN.pdf", "Resume_Universal_EN.pdf", "Universal", "EN"),
            
            ResumeItem("Mobile Developer CV", "Profil Profesional", "PDF", "170 KB", "pdf/CV_MobileDeveloper_ID.pdf", "CV_MobileDeveloper_ID.pdf", "Mobile", "ID"),
            ResumeItem("Quality Assurance CV", "Profil Profesional", "PDF", "165 KB", "pdf/CV_QA_ID.pdf", "CV_QA_ID.pdf", "QA", "ID"),
            ResumeItem("Universal CV", "Profil Profesional", "PDF", "165 KB", "pdf/CV_Universal_ID.pdf", "CV_Universal_ID.pdf", "Universal", "ID"),
            ResumeItem("Mobile Developer Resume", "Resume Singkat", "PDF", "159 KB", "pdf/Resume_MobileDeveloper_ID.pdf", "Resume_MobileDeveloper_ID.pdf", "Mobile", "ID"),
            ResumeItem("Quality Assurance Resume", "Resume Singkat", "PDF", "157 KB", "pdf/Resume_QA_ID.pdf", "Resume_QA_ID.pdf", "QA", "ID"),
            ResumeItem("Universal Resume", "Resume Singkat", "PDF", "157 KB", "pdf/Resume_Universal_ID.pdf", "Resume_Universal_ID.pdf", "Universal", "ID")
        )
    }

    val filteredResumes = resumes.filter { item ->
        (selectedLanguage == "ALL" || item.language == selectedLanguage) &&
        (selectedCategory == "ALL" || item.category == selectedCategory)
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
                text = "Resumes & CVs",
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
                    contentDescription = "Resume Animation",
                    modifier = Modifier.size(64.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Explore my comprehensive resumes and curriculum vitae tailored for different roles",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Filter Controls Container
        Column(
            modifier = Modifier
                .widthIn(max = 1000.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(GlassBackground)
                .border(1.dp, GlassBorder, RoundedCornerShape(24.dp))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Language Filter Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Text("Language: ", color = TextSecondary, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                listOf("ALL" to "All", "EN" to "🇬🇧 English", "ID" to "🇮🇩 Indonesia").forEach { (code, label) ->
                    val isSelected = selectedLanguage == code
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) AccentPurple else Color.Transparent)
                            .border(1.dp, if (isSelected) Color.Transparent else GlassBorder, RoundedCornerShape(12.dp))
                            .clickable { selectedLanguage = code }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(label, color = if (isSelected) BackgroundDark else TextPrimary, style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            // Category Filter Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Text("Role: ", color = TextSecondary, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                listOf("ALL" to "All Roles", "Mobile" to "Mobile Developer", "QA" to "Quality Assurance", "Universal" to "Universal").forEach { (code, label) ->
                    val isSelected = selectedCategory == code
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) AccentIndigo else Color.Transparent)
                            .border(1.dp, if (isSelected) Color.Transparent else GlassBorder, RoundedCornerShape(12.dp))
                            .clickable { selectedCategory = code }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(label, color = if (isSelected) BackgroundDark else TextPrimary, style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Document Cards
        FlowRow(
            modifier = Modifier.fillMaxWidth().widthIn(max = 1200.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            filteredResumes.forEach { item ->
                ResumeCard(
                    title = item.title,
                    subtitle = item.subtitle,
                    type = item.type,
                    size = item.size,
                    pdfUrl = item.pdfUrl,
                    filename = item.filename,
                    category = item.category,
                    language = item.language
                )
            }
        }
    }
}

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@Composable
private fun ResumeCard(
    title: String,
    subtitle: String,
    type: String,
    size: String,
    pdfUrl: String,
    filename: String,
    category: String,
    language: String
) {
    var isHovered by remember { mutableStateOf(false) }

    val iconVector = when (category) {
        "Mobile" -> FeatherIcons.Smartphone
        "QA" -> FeatherIcons.CheckSquare
        else -> FeatherIcons.FileText
    }

    Column(
        modifier = Modifier
            .width(280.dp)
            .hoverScale(1.03f)
            .clip(RoundedCornerShape(24.dp))
            .background(GlassBackground)
            .border(
                width = 1.dp,
                color = if (isHovered) AccentIndigo else GlassBorder,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { openPdfViewer(pdfUrl) }
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AccentIndigo.copy(alpha = 0.15f))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    tint = AccentIndigo,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            // Language Badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(AccentPurple.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = language,
                    color = AccentPurple,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = size,
                style = MaterialTheme.typography.labelLarge.copy(color = TextSecondary)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(AccentIndigo.copy(alpha = 0.2f))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = FeatherIcons.Eye,
                        contentDescription = "Preview",
                        tint = AccentIndigo,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(AccentPurple.copy(alpha = 0.2f))
                        .clickable { downloadFile(pdfUrl, filename) }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(
                            imageVector = FeatherIcons.Download,
                            contentDescription = "Download",
                            tint = AccentPurple,
                            modifier = Modifier.size(16.dp)
                        )
                        Text("Save", color = AccentPurple, style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}
