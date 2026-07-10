package com.allenth17.portofolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.allenth17.portofolio.theme.GlassBackground
import com.allenth17.portofolio.theme.GlassBorder
import com.allenth17.portofolio.theme.TextSecondary

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(GlassBackground)
            .border(1.dp, GlassBorder)
            .padding(vertical = 24.dp, horizontal = 40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "© 2026 Thiflul Ma'ani Minal Mu'min™. All Rights Reserved.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )
    }
}
