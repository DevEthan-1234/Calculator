package com.example.calculator.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UnitConverterScreen(isDarkMode: Boolean = false) {
    val backgroundColor = if (isDarkMode) Color(0xFF101010) else Color(0xFFFFFFFF)
    val textColor = if (isDarkMode) Color.White else Color.Black

    val categories = listOf("Area", "Length", "Temperature", "Volume", "Mass", "Data", "Speed", "Time")
    var selectedCategory by remember { mutableStateOf("Length") }

    val units = mapOf(
        "Area" to listOf("Square Meters", "Square Kilometers", "Hectares", "Acres"),
        "Length" to listOf("Meters", "Kilometers", "Miles"),
        "Temperature" to listOf("Celsius", "Fahrenheit", "Kelvin"),
        "Volume" to listOf("Liters", "Milliliters", "Cubic Meters", "Gallons"),
        "Mass" to listOf("Grams", "Kilograms", "Pounds"),
        "Data" to listOf("Bytes", "Kilobytes", "Megabytes", "Gigabytes"),
        "Speed" to listOf("Meters/Second", "Kilometers/Hour", "Miles/Hour"),
        "Time" to listOf("Seconds", "Minutes", "Hours")
    )

    var fromUnit by remember { mutableStateOf("Meters") }
    var toUnit by remember { mutableStateOf("Kilometers") }
    var inputValue by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    fun convert(): String {
        val input = inputValue.toDoubleOrNull() ?: return ""
        return when (selectedCategory) {
            "Length" -> when (fromUnit to toUnit) {
                "Meters" to "Kilometers" -> (input / 1000).toString()
                "Kilometers" to "Meters" -> (input * 1000).toString()
                "Miles" to "Kilometers" -> (input * 1.60934).toString()
                "Kilometers" to "Miles" -> (input / 1.60934).toString()
                else -> input.toString()
            }
            "Mass" -> when (fromUnit to toUnit) {
                "Grams" to "Kilograms" -> (input / 1000).toString()
                "Kilograms" to "Grams" -> (input * 1000).toString()
                "Pounds" to "Kilograms" -> (input * 0.453592).toString()
                "Kilograms" to "Pounds" -> (input / 0.453592).toString()
                else -> input.toString()
            }
            "Temperature" -> when (fromUnit to toUnit) {
                "Celsius" to "Fahrenheit" -> ((input * 9 / 5) + 32).toString()
                "Fahrenheit" to "Celsius" -> ((input - 32) * 5 / 9).toString()
                "Celsius" to "Kelvin" -> (input + 273.15).toString()
                "Kelvin" to "Celsius" -> (input - 273.15).toString()
                else -> input.toString()
            }
            "Area" -> when (fromUnit to toUnit) {
                "Square Meters" to "Square Kilometers" -> (input / 1_000_000).toString()
                "Square Kilometers" to "Square Meters" -> (input * 1_000_000).toString()
                "Hectares" to "Acres" -> (input * 2.47105).toString()
                "Acres" to "Hectares" -> (input / 2.47105).toString()
                else -> input.toString()
            }
            "Volume" -> when (fromUnit to toUnit) {
                "Liters" to "Milliliters" -> (input * 1000).toString()
                "Milliliters" to "Liters" -> (input / 1000).toString()
                "Cubic Meters" to "Liters" -> (input * 1000).toString()
                "Gallons" to "Liters" -> (input * 3.78541).toString()
                else -> input.toString()
            }
            "Data" -> when (fromUnit to toUnit) {
                "Bytes" to "Kilobytes" -> (input / 1024).toString()
                "Kilobytes" to "Megabytes" -> (input / 1024).toString()
                "Megabytes" to "Gigabytes" -> (input / 1024).toString()
                else -> input.toString()
            }
            "Speed" -> when (fromUnit to toUnit) {
                "Meters/Second" to "Kilometers/Hour" -> (input * 3.6).toString()
                "Kilometers/Hour" to "Miles/Hour" -> (input * 0.621371).toString()
                else -> input.toString()
            }
            "Time" -> when (fromUnit to toUnit) {
                "Seconds" to "Minutes" -> (input / 60).toString()
                "Minutes" to "Hours" -> (input / 60).toString()
                "Hours" to "Minutes" -> (input * 60).toString()
                else -> input.toString()
            }
            else -> input.toString()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Unit Converter", color = textColor, fontSize = 26.sp)

        DropdownSelector("Category", categories, selectedCategory, {
            selectedCategory = it
            fromUnit = units[it]!!.first()
            toUnit = units[it]!!.last()
        }, isDarkMode)

        DropdownSelector("From", units[selectedCategory] ?: listOf(), fromUnit, { fromUnit = it }, isDarkMode)
        DropdownSelector("To", units[selectedCategory] ?: listOf(), toUnit, { toUnit = it }, isDarkMode)

        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text("Enter Value", color = textColor) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Cyan,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.Magenta
            )
        )

        Button(
            onClick = { result = convert() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4))
        ) {
            Text("Convert", fontSize = 18.sp, color = Color.White)
        }

        if (result.isNotEmpty()) {
            Text(
                text = "Result: $result",
                color = textColor,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isDarkMode: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    val textColor = if (isDarkMode) Color.White else Color.Black
    val bgColor = if (isDarkMode) Color.DarkGray else Color(0xFFF1F1F1)

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = bgColor)
        ) {
            Text("$label: $selectedOption", color = textColor)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = textColor)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onOptionSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewUnitConverterScreen() {
    UnitConverterScreen()
}