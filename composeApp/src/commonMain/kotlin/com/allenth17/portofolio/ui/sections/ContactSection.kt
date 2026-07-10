package com.allenth17.portofolio.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.allenth17.portofolio.theme.AccentIndigo
import com.allenth17.portofolio.theme.AccentPurple
import com.allenth17.portofolio.theme.GlassBackground
import com.allenth17.portofolio.theme.GlassBorder
import com.allenth17.portofolio.theme.TextPrimary
import com.allenth17.portofolio.theme.TextSecondary
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.style.TextAlign
import com.allenth17.portofolio.ui.components.hoverScale
import com.allenth17.portofolio.theme.BackgroundDark


@OptIn(org.jetbrains.compose.resources.ExperimentalResourceApi::class)
@Composable
fun ContactSection(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var jsonString by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val bytes = portofolio.composeapp.generated.resources.Res.readBytes("files/w3e4r5t6y7u8.json")
            jsonString = bytes.decodeToString()
        } catch (e: Exception) {
            println("Failed to load Lottie: ${e.message}")
        }
    }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val isDesktop = maxWidth > 900.dp

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = if (isDesktop) 80.dp else 24.dp, vertical = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Get In Touch",
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
                if (jsonString != null) {
                    val composition by io.github.alexzhirkevich.compottie.rememberLottieComposition {
                        io.github.alexzhirkevich.compottie.LottieCompositionSpec.JsonString(jsonString!!)
                    }

                    androidx.compose.foundation.Image(
                        painter = io.github.alexzhirkevich.compottie.rememberLottiePainter(
                            composition = composition,
                            iterations = io.github.alexzhirkevich.compottie.Compottie.IterateForever
                        ),
                        contentDescription = "Contact Animation",
                        modifier = Modifier
                            .size(64.dp)
                    )
                } else {
                    Box(modifier = Modifier.size(64.dp).background(GlassBackground, RoundedCornerShape(24.dp)))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Have a project in mind? Let's work together.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(60.dp))

            Box(
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ContactForm(
                    name = name,
                    onNameChange = { name = it },
                    email = email,
                    onEmailChange = { email = it },
                    message = message,
                    onMessageChange = { message = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ContactForm(
    name: String, onNameChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    message: String, onMessageChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(GlassBackground)
            .border(1.dp, GlassBorder, RoundedCornerShape(24.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        InputRow("Name", "Your full name", name, onNameChange)
        InputRow("Email", "Your email address", email, onEmailChange)
        InputRow("Message", "Tell me about your project", message, onMessageChange, isTextArea = true)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .hoverScale(1.02f)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.linearGradient(listOf(AccentPurple, AccentIndigo)))
                .clickable {
                    if (name.isBlank() || email.isBlank() || message.isBlank()) {
                        showAlert("Please fill all fields before sending the message.")
                    } else {
                        sendEmailJs(name, email, message)
                    }
                }
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Send Message",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = BackgroundDark
            )
        }
    }
}

@Composable
fun InputRow(label: String, placeholder: String, value: String, onValueChange: (String) -> Unit, isTextArea: Boolean = false) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = TextPrimary
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = TextSecondary.copy(alpha = 0.5f)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isTextArea) 120.dp else 56.dp)
                .background(GlassBackground, RoundedCornerShape(12.dp)),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentPurple,
                unfocusedBorderColor = GlassBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = AccentPurple,
                unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
            maxLines = if (isTextArea) 5 else 1,
            singleLine = !isTextArea
        )
    }
}

private fun showAlert(msg: String) {
    js("window.alert(msg)")
}

private fun sendEmailJs(name: String, email: String, message: String) {
    js("window.fetch('https://script.google.com/macros/s/AKfycbyQ8u16SEuGrreTcNn7Ffa3ixOJ_4xYLZxm58jxZdnjs25SvSYuoqvA2QdDcwFwQihIjg/exec', {method: 'POST', mode: 'no-cors', headers: {'Content-Type': 'text/plain'}, body: JSON.stringify({name: name, email: email, message: message})}).then(response => { window.alert('Message sent successfully!'); }).catch(error => { window.alert('Error sending email.'); });")
}
