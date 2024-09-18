package com.example.resistencias.Elementos


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.DropdownList
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Banda(val color: String, val valor: Int, val multiplicador: Double)

val ListaColores = listOf(
    Banda("",0,0.0),
    Banda("Negro", 1, 1.0),
    Banda("Marrón", 2, 10.0),
    Banda("Rojo", 3, 100.0),
    Banda("Naranja", 4, 1000.0),
    Banda("Amarillo", 5, 10000.0),
    Banda("Verde", 6, 100000.0),
    Banda("Azul", 7, 1000000.0),
    Banda("Violeta", 8, 10000000.0),
    Banda("Gris", 9, 100000000.0),
    Banda("Blanco", 10, 1000000000.0),
)



data class Tolerancia(val color: String, val tolerance: String)
val OpcionTole = listOf(
    Tolerancia("Seleccionar",""),
    Tolerancia("Dorado", "±5%"),
    Tolerancia("Plateado", "±10%")
)

fun calcularResistencia(banda1: Banda, banda2: Banda, banda3: Banda): Double {
    return ((banda1.valor * 10) + banda2.valor) * banda3.multiplicador
}


@Preview(showBackground = true)
@Composable
fun Inicio() {

    val context = LocalContext.current

    var ColorBanda1 by remember { mutableStateOf(ListaColores[0]) }
    var ColorBanda2 by remember { mutableStateOf(ListaColores[0]) }
    var ColorBanda3 by remember { mutableStateOf(ListaColores[0]) }
    var ToleranciaB by remember { mutableStateOf(OpcionTole[0]) }
    var ValorFinal by remember { mutableStateOf(0.0) }


    val valorResistencia = calcularResistencia(ColorBanda1, ColorBanda2, ColorBanda3)

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(Color.White)
            .border(2.dp, Color.Blue),
        verticalArrangement = Arrangement.Center

    ) {
        item {
            Text(
                text = "Calculadora De Resistencias",
                color = Color.Blue,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive

            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "El Primer Color",
                color = Color.Blue,
                fontSize = 20.sp,
                fontFamily = FontFamily.Cursive
            )
            DropdownList(
                selectedBand = ColorBanda1,
                onSelectedChange = { ColorBanda1 = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text="El Segundo Color",
                color = Color.Blue,
                fontSize = 20.sp,
                fontFamily = FontFamily.Cursive)
            DropdownList(
                selectedBand = ColorBanda2,
                onSelectedChange = { ColorBanda2 = it }
            )


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text="El Trecer Color",
                color = Color.Blue,
                fontSize = 20.sp,
                fontFamily = FontFamily.Cursive)
            DropdownList(
                selectedBand = ColorBanda3,
                onSelectedChange = { ColorBanda3 = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text("Tolerancia",
                color = Color.Blue,
                fontSize = 24.sp,
                fontFamily = FontFamily.Cursive)
            ToleranceDropdownList(
                selectedTolerance = ToleranciaB,
                onSelectedChange = { ToleranciaB = it },
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    ValorFinal = calcularResistencia(ColorBanda1, ColorBanda2, ColorBanda3)
                    Toast.makeText(context, "El valor es:${valorResistencia}Ω", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
            ){
                Text(text = "Calcular",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Cursive)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Tolerancia: ${ToleranciaB.tolerance}", fontSize = 20.sp, fontFamily = FontFamily.Cursive)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownList(
    selectedBand: Banda,
    onSelectedChange: (Banda) -> Unit
) {
    var estado by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded =estado ,
        onExpandedChange = {
            estado = !estado
        }
    ) {
        TextField(
            value = selectedBand.color,
            onValueChange ={},
            readOnly = true,
            label = {Text("Seleccionar")},
            trailingIcon = {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = estado,
            onDismissRequest = { estado = false }
        ) {
            ListaColores.forEach { band ->
                DropdownMenuItem(onClick = {
                    onSelectedChange(band)
                    estado = false
                }) {
                    Text(band.color)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToleranceDropdownList(
    selectedTolerance: Tolerancia,
    onSelectedChange: (Tolerancia) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedTolerance.color,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            OpcionTole.forEach { tolerance ->
                DropdownMenuItem(onClick = {
                    onSelectedChange(tolerance)
                    expanded = false
                }) {
                    Text(tolerance.color)
                }
            }
        }
    }
}


fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {
}
