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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.foundation.focusable
import androidx.compose.ui.input.key.*
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource

@Composable
fun App() {
    PortfolioTheme {
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            hideLoadingScreen()
            focusRequester.requestFocus()
        }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusRequester.requestFocus()
                }
                .drawBehind {
                    val gridSize = 56.dp.toPx()
                    val gridColor = com.allenth17.portofolio.theme.GlassBorder.copy(alpha = 0.4f)
                    val strokeWidth = 1.dp.toPx()

                    // Vertical lines
                    var x = 0f
                    while (x < size.width) {
                        drawLine(
                            color = gridColor,
                            start = Offset(x, 0f),
                            end = Offset(x, size.height),
                            strokeWidth = strokeWidth
                        )
                        x += gridSize
                    }

                    // Horizontal lines
                    var y = 0f
                    while (y < size.height) {
                        drawLine(
                            color = gridColor,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                        y += gridSize
                    }
                }
        ) {
            val viewportHeight = maxHeight
            val lazyListState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .focusRequester(focusRequester)
                    .focusable()
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.type == KeyEventType.KeyDown) {
                            when (keyEvent.key) {
                                Key.DirectionDown -> {
                                    coroutineScope.launch {
                                        lazyListState.animateScrollBy(150f)
                                    }
                                    true
                                }
                                Key.DirectionUp -> {
                                    coroutineScope.launch {
                                        lazyListState.animateScrollBy(-150f)
                                    }
                                    true
                                }
                                else -> false
                            }
                        } else {
                            false
                        }
                    }
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = viewportHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        EntranceAnimation { HeroSection() }
                    }
                }
                
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = viewportHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        EntranceAnimation { AboutSection() }
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = viewportHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        EntranceAnimation { JourneySection() }
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = viewportHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        EntranceAnimation { PortfolioSection() }
                    }
                }
                
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = viewportHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        EntranceAnimation { CertificateSection() }
                    }
                }
                
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = viewportHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        EntranceAnimation { ResumeSection() }
                    }
                }

                item {
                    EntranceAnimation { TechMarqueeSection() }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = viewportHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        EntranceAnimation { ContactSection() }
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
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