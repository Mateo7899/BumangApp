package com.example.bumangapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bumangapp.network.RetrofitClient
import com.example.bumangapp.network.SessionManager
import kotlinx.coroutines.launch


@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val scope = rememberCoroutineScope()
    val currentRoute = "settings"
    
    var notificationsEnabled by remember { mutableStateOf(false) }
    val isPremium = sessionManager.isPremium()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Título
        Text(
            text = "Configuración",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222222)
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
        )

        // Contenido principal con peso
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(vertical = 4.dp)
        ) {
            if (!isPremium) {
                SettingItem(
                    icon = Icons.Default.Star,
                    iconColor = Color(0xFFFFD700),
                    text = "Obtener Premium",
                    onClick = { navController.navigate("premium") }
                )
                Divider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))
            } else {
                SettingItem(
                    icon = Icons.Default.Verified,
                    iconColor = Color(0xFF4CAF50),
                    text = "Beneficios Premium",
                    onClick = { navController.navigate("premium") }
                )
                Divider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))
            }

            SettingItem(
                icon = Icons.Default.Key,
                iconColor = Color(0xFFEDE430),
                text = "Cambiar contraseña",
                onClick = { navController.navigate("change_password") }
            )
            Divider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))

            SettingItem(
                icon = Icons.Default.TextFields,
                iconColor = Color(0xFFEDE430),
                text = "Tamaño de texto",
                onClick = { navController.navigate("text_size") }
            )
            Divider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))

            SettingCheckItem(
                icon = Icons.Default.Notifications,
                iconColor = Color(0xFFEDE430),
                text = "Notificaciones",
                checked = notificationsEnabled,
                onCheckedChange = { isChecked ->
                    notificationsEnabled = isChecked
                }
            )
            Divider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))


            // Cerrar sesión
            SettingItem(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                iconColor = Color(0xFFE74C3C),
                text = "Cerrar sesión",
                onClick = {
                    scope.launch {
                        try {
                            val token = sessionManager.fetchAuthToken()
                            if (token != null) {
                                RetrofitClient.instance.logout("Bearer $token")
                            }
                        } catch (e: Exception) {
                            // Ignorar error en logout si falla la red
                        } finally {
                            sessionManager.clearSession()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                }
            )
        }

        // Barra inferior
        BottomBar(navController = navController, currentRoute = currentRoute)
    }
}

@Composable
fun SettingItem(
    icon: ImageVector,
    iconColor: Color,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Go",
            tint = Color.Gray
        )
    }
}

@Composable
fun SettingCheckItem(
    icon: ImageVector,
    iconColor: Color,
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 14.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
