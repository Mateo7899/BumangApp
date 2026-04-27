package com.example.bumangapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bumangapp.ui.theme.BumangRed
import com.example.bumangapp.ui.theme.BumangWhite

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // New Bus Logo Placeholder (User's Gray circle icon should be used here)
            // For now using ic_bus if available, or a generic placeholder
            Icon(
                painter = painterResource(id = R.drawable.ic_bus),
                contentDescription = "BumangApp Logo",
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(100.dp))
                    .padding(24.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "BumangApp",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "BIENVENIDO",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BumangRed),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "INICIAR SESION", color = BumangWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BumangRed),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "REGISTRARSE", color = BumangWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
