package com.example.bumangapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
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
import com.example.bumangapp.network.RetrofitClient
import com.example.bumangapp.network.SessionManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumScreen(navController: NavController) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val scope = rememberCoroutineScope()
    var isPremium by remember { mutableStateOf(sessionManager.isPremium()) }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BumangApp Premium") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (isPremium) "¡Ya eres Premium!" else "Desbloquea BumangApp Premium",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Disfruta de beneficios exclusivos para moverte mejor por Bucaramanga.",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            PremiumBenefitItem("Viajes gratis mensuales")
            PremiumBenefitItem("Descuentos en pasajes")
            PremiumBenefitItem("Soporte prioritario")
            PremiumBenefitItem("Sin publicidad")

            Spacer(modifier = Modifier.weight(1f))

            if (!isPremium) {
                Button(
                    onClick = {
                        isLoading = true
                        scope.launch {
                            try {
                                val token = sessionManager.fetchAuthToken()
                                val response = RetrofitClient.instance.getUser("Bearer $token")
                                
                                val user = response.body()
                                if (user != null) {
                                    // Simulación de compra exitosa
                                    sessionManager.saveUser(user) // Actualiza con lógica getIsPremium interna
                                    // Forzamos el flag en el manager para la demo
                                    val prefs = context.getSharedPreferences("bumang_prefs", android.content.Context.MODE_PRIVATE)
                                    prefs.edit().putBoolean("is_premium", true).apply()
                                    isPremium = true
                                }
                            } catch (e: Exception) {
                                // Error
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE1131F)),
                    enabled = !isLoading
                ) {
                    Text(if (isLoading) "Procesando..." else "Comprar por $9.900/mes", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Text(
                    text = "Gracias por apoyar BumangApp",
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun PremiumBenefitItem(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, fontSize = 16.sp)
    }
}
