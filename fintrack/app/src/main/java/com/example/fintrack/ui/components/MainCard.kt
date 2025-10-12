package com.example.fintrack.ui.components


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin

@Preview(showBackground = true, backgroundColor = 0xFF0A0F24)
@Composable
fun FinTrackMainCard() {
    var isPressed by remember { mutableStateOf(false) }

    // Animate card press feedback
    val animatedElevation by animateDpAsState(if (isPressed) 28.dp else 10.dp, tween(400))
    val gradientShift by animateFloatAsState(if (isPressed) 250f else 0f, tween(800))
    val cardCorner = 28.dp

    // Gradient base colors
    val startColor = Color(0xFF001B79)
    val endColor = Color(0xFF00C2FF)
    val pulseColor by animateColorAsState(
        if (isPressed) Color(0xFF8AFFE0) else Color.White.copy(alpha = 0.05f),
        tween(600)
    )

    // Animate subtle pulse on the balance number
    val infiniteTransition = rememberInfiniteTransition()
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            }
            .graphicsLayer {
                rotationX = if (isPressed) 3f else 0f
                rotationY = if (isPressed) 3f else 0f
                cameraDistance = 12f * density
            },
        shape = RoundedCornerShape(cardCorner),
        elevation = CardDefaults.cardElevation(defaultElevation = animatedElevation),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            startColor,
                            Color(0xFF002C9E),
                            endColor
                        ),
                        start = Offset(gradientShift, 0f),
                        end = Offset(500f, 500f)
                    )
                )
                .border(
                    width = 1.2.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.25f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(cardCorner)
                )
                .padding(24.dp)
        ) {
            // Glass reflection overlay
            Box(
                Modifier
                    .matchParentSize()
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            Brush.linearGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.12f),
                                    Color.Transparent,
                                    Color.White.copy(alpha = 0.05f)
                                ),
                                start = Offset.Zero,
                                end = Offset(size.width, size.height)
                            )
                        )
                    }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "FinTrack Balance",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    )
                    Text(
                        text = "VISA",
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Column {
                    Text(
                        text = "$61,547.89",
                        color = pulseColor,
                        fontSize = (34.sp * pulse),
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.shadow(
                            elevation = 12.dp,
                            ambientColor = Color.Cyan.copy(alpha = 0.3f),
                            spotColor = Color.Cyan.copy(alpha = 0.6f)
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "+$530.45 today",
                        color = Color(0xFF8AFFE0),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Updated just now",
                        color = Color.White.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "•••",
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}