package com.example.bumangapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bumangapp.network.SessionManager

@Composable
fun TextSizeScreen(navController: NavController) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    var selectedSize by remember { mutableStateOf(sessionManager.fetchTextSize()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tamaño del texto",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF222222)
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextSizeOption("Pequeño", selectedSize) { selectedSize = "Pequeño" }
        TextSizeOption("Mediano", selectedSize) { selectedSize = "Mediano" }
        TextSizeOption("Grande", selectedSize) { selectedSize = "Grande" }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Vista previa del texto",
            fontSize = when (selectedSize) {
                "Pequeño" -> 12.sp
                "Mediano" -> 16.sp
                "Grande" -> 20.sp
                else -> 16.sp
            },
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = { 
                sessionManager.saveTextSize(selectedSize)
                navController.popBackStack() 
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE1131F),
                contentColor = Color.White
            )
        ) {
            Text("Guardar", fontSize = 16.sp)
        }
    }
}

@Composable
fun TextSizeOption(
    label: String,
    selected: String,
    onClick: () -> Unit
) {
    val isSelected = label == selected
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = if (isSelected) Color(0xFFEDE430).copy(alpha = 0.2f) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = if (isSelected) Color(0xFF222222) else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFFEDE430)
            )
        }
    }
}
