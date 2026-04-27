package com.example.bumangapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bumangapp.ui.theme.BumangRed


@Composable
fun BottomBar(navController: NavController, currentRoute: String) {
    Surface(
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomIconItem(
                icon = Icons.Default.Map,
                label = "Mapa",
                color = if (currentRoute == "map") BumangRed else Color.Gray
            ) {
                if (currentRoute != "map") {
                    navController.navigate("map") {
                        popUpTo("map") { inclusive = true }
                    }
                }
            }

            BottomIconItem(
                icon = Icons.Default.Route,
                label = "Rutas",
                color = if (currentRoute == "route") BumangRed else Color.Gray
            ) {
                if (currentRoute != "route") {
                    navController.navigate("route") {
                        launchSingleTop = true
                    }
                }
            }

            // [NEW] Premium Button in Menu
            BottomIconItem(
                icon = Icons.Default.Star,
                label = "Premium",
                color = if (currentRoute == "premium") BumangRed else Color.Gray
            ) {
                if (currentRoute != "premium") {
                    navController.navigate("premium") {
                        launchSingleTop = true
                    }
                }
            }

            BottomIconItem(
                icon = Icons.Default.Settings,
                label = "Ajustes",
                color = if (currentRoute == "settings") BumangRed else Color.Gray
            ) {
                if (currentRoute != "settings") {
                    navController.navigate("settings") {
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}

@Composable
fun BottomIconItem(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(26.dp)
        )
        Text(
            text = label,
            color = color,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
