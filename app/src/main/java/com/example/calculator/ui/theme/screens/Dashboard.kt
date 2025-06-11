package com.example.calculator.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorScreen() {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }

    val dropdownItems = listOf("Scientific Functions", "Unit Converter")
    val backgroundColor = if (isDarkMode) Color(0xFF101010) else Color(0xFFFFFFFF)
    val textColor = if (isDarkMode) Color.White else Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        // Top bar: Dropdown & Theme Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Dropdown Menu
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { expanded = true }
                        .background(Color(0xFF6200EE), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(text = "Options", color = Color.White)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        tint = Color.White
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    dropdownItems.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                expanded = false
                                // Handle dropdown selection
                            }
                        )
                    }
                }
            }

            // Dark Mode Toggle
            IconButton(onClick = { isDarkMode = !isDarkMode }) {
                Icon(
                    imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = "Toggle Theme",
                    tint = if (isDarkMode) Color.Yellow else Color.Black
                )
            }
        }

        // Display Section
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = expression,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            fontSize = 32.sp,
            color = textColor,
            textAlign = TextAlign.End
        )
        Text(
            text = result,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            fontSize = 24.sp,
            color = if (isDarkMode) Color.Gray else Color.DarkGray,
            textAlign = TextAlign.End
        )

        // Buttons
        val buttons = listOf(
            listOf("Shift", "(", ")", "DEL"),
            listOf("sin", "cos", "tan", "÷"),
            listOf("7", "8", "9", "×"),
            listOf("4", "5", "6", "−"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "=", "Ans")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { label ->
                    CalculatorButton(
                        label = label,
                        isDarkMode = isDarkMode,
                        modifier = Modifier.weight(1f)
                    ) {
                        when (label) {
                            "=" -> result = "Result" // Replace with your evaluation logic
                            "DEL" -> expression = expression.dropLast(1)
                            else -> expression += label
                        }
                    }
                }
            }
        }

        // Bottom full-width button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            CalculatorButton(
                label = "Convert Units",
                isDarkMode = isDarkMode,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Navigate to unit converter
            }
        }
    }
}

@Composable
fun CalculatorButton(label: String, isDarkMode: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val bgColor = when (label) {
        "=" -> Color(0xFF4CAF50)
        "DEL" -> Color(0xFFF44336)
        "Convert Units" -> Color(0xFF03A9F4)
        "Shift", "sin", "cos", "tan" -> Color(0xFFFFC107)
        "+", "−", "×", "÷" -> Color(0xFF9C27B0)
        else -> if (isDarkMode) Color.DarkGray else Color(0xFFE0E0E0)
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = label, fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorScreen() {
    CalculatorScreen()
}
