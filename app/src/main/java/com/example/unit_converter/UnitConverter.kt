package com.example.unit_converter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun Converter(modifier: Modifier = Modifier) {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("0.0") }

    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }

    var isInputMenuExpanded by remember { mutableStateOf(false) }
    var isOutputMenuExpanded by remember { mutableStateOf(false) }

    var unitConversionFactor by remember { mutableDoubleStateOf(1.0) }
    var outputConversionFactor by remember { mutableDoubleStateOf(1.0) }

    fun convertUnits() {
        val input = inputValue.toDoubleOrNull() ?: 0.0
        val result =
            ((input * unitConversionFactor / outputConversionFactor) * 100).roundToInt() / 100
        outputValue = result.toString()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Unit Converter",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
            },
            placeholder = { Text(text = "Enter Value") },
            singleLine = true,
            label = { Text(text = "Enter Value") }, modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            DropdownMenuButton(
                label = inputUnit,
                expended = isInputMenuExpanded,
                onExpandedChange = { isInputMenuExpanded = it },
                onOptionSelected = { unit, factor ->
                    inputUnit = unit
                    unitConversionFactor = factor
                    convertUnits()
                }

            )
            DropdownMenuButton(
                label = outputUnit,
                expended = isOutputMenuExpanded,
                onExpandedChange = { isOutputMenuExpanded = it },
                onOptionSelected = { unit, factor ->
                    outputUnit = unit
                    outputConversionFactor = factor
                    convertUnits()
                }

            )

        }
        Spacer(modifier = Modifier.height(15.dp))

        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Result: $outputValue $outputUnit")
    }

}

@Composable
fun DropdownMenuButton(
    label: String,
    expended: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onOptionSelected: (String, Double) -> Unit
) {
    Box() {
        Button(
            onClick = { onExpandedChange(!expended) },
            modifier = Modifier.wrapContentSize()

        ) {
            Text(text = label)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.rotate(if (expended) 180f else 0f)
            )
        }
        DropdownMenu(
            expanded = expended,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            listOf(
                "Meters" to 1.0,
                "Centimeter" to 0.01,
                "Feet" to 0.3048,
                "Miles" to 1609.35,
                "Millimeter" to 0.001
            ).forEach { (unit, factor) ->
                DropdownMenuItem(
                    text = { Text(text = unit) },
                    onClick = {
                        onExpandedChange(false)
                        onOptionSelected(unit, factor)
                    }
                )
            }
        }
    }
}


