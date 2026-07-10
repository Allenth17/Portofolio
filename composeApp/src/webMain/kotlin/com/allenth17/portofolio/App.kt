package com.allenth17.portofolio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.allenth17.portofolio.theme.BackgroundDark
import com.allenth17.portofolio.theme.PortfolioTheme
import com.allenth17.portofolio.ui.components.NavBar
import com.allenth17.portofolio.ui.sections.HeroSection
import com.allenth17.portofolio.ui.sections.AboutSection
import com.allenth17.portofolio.ui.sections.PortfolioSection
import com.allenth17.portofolio.ui.sections.ContactSection
import com.allenth17.portofolio.ui.sections.JourneySection
import com.allenth17.portofolio.ui.sections.ResumeSection
import com.allenth17.portofolio.ui.sections.CertificateSection
import com.allenth17.portofolio.ui.sections.TechMarqueeSection
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.fadeOut
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutVertically
import com.allenth17.portofolio.ui.components.EntranceAnimation
import kotlinx.coroutines.launch
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.allenth17.portofolio.theme.AccentPurple
import com.allenth17.portofolio.theme.AccentIndigo

@Composable
fun App() {
    PortfolioTheme {
        LaunchedEffect(Unit) {
            hideLoadingScreen()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(AccentPurple.copy(alpha = 0.08f), Color.Transparent),
                            center = Offset(size.width * 0.85f, size.height * 0.15f),
                            radius = size.width * 0.5f
                        )
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(AccentIndigo.copy(alpha = 0.08f), Color.Transparent),
                            center = Offset(size.width * 0.12f, size.height * 0.45f),
                            radius = size.width * 0.5f
                        )
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(AccentPurple.copy(alpha = 0.06f), Color.Transparent),
                            center = Offset(size.width * 0.88f, size.height * 0.75f),
                            radius = size.width * 0.55f
                        )
                    )
                }
        ) {
            val lazyListState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize()
            ) {
                item {

                    NavBar(
                        modifier = Modifier,
                        onNavigate = { targetIndex ->
                            coroutineScope.launch {
                                lazyListState.animateScrollToItem(targetIndex)
                            }
                        }
                    )
                }

                item {
                    Box(modifier = Modifier.onGloballyPositioned {  }) {
                        EntranceAnimation { HeroSection() }
                    }
                }
                item { EntranceAnimation { AboutSection() } }

                item { EntranceAnimation { JourneySection() } }

                item { EntranceAnimation { PortfolioSection() } }
                item { EntranceAnimation { CertificateSection() } }
                item { EntranceAnimation { ResumeSection() } }

                item { EntranceAnimation { TechMarqueeSection() } }

                item { EntranceAnimation { ContactSection() } }

                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp), contentAlignment = Alignment.BottomCenter) {
                        EntranceAnimation {
                            com.allenth17.portofolio.ui.components.Footer()
                        }
                    }
                }
            }

        }
    }
}

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
private fun hideLoadingScreen() {
    js("""
        const loader = document.getElementById('loading-screen');
        if (loader) {
            loader.style.opacity = '0';
            loader.style.visibility = 'hidden';
            setTimeout(() => {
                loader.remove();
            }, 400);
        }
    """)
}