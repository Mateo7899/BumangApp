package com.example.bumangapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PrivacyScreen(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.ic_bumang_logo), // 👈 Actualizado
            contentDescription = "Logo BumangApp",
            modifier = Modifier
                .size(120.dp)
                .padding(top = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Título
        Text(
            text = "Privacidad y Valores",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222222)
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Misión
        Text(
            text = "Misión:\nBrindar una plataforma tecnológica innovadora que optimice la experiencia de transporte público en Bucaramanga, facilitando a los usuarios información en tiempo real, rutas eficientes y una movilidad más segura, accesible y sostenible. MoveBGA busca conectar a las personas con la ciudad de forma práctica y confiable, mejorando la calidad del servicio y fomentando una cultura de movilidad inteligente.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Visión
        Text(
            text = "Visión:\nSer la aplicación líder en movilidad urbana inteligente en Bucaramanga y el oriente colombiano para 2030, reconocida por transformar el transporte público mediante la tecnología, promoviendo ciudades más conectadas, sostenibles y centradas en las necesidades de sus ciudadanos.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Política de Privacidad
        Text(
            text = "Política de Privacidad:",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222222)
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "En MoveBGA valoramos tu privacidad. Toda la información personal que recopilemos será utilizada únicamente para mejorar tu experiencia en el transporte urbano, garantizando la seguridad y confidencialidad de tus datos.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para abrir PDF de Política de Privacidad
        Button(
            onClick = {
                val pdfUrl = "https://drive.google.com/file/d/13QwnBZtKJ8iwI-bZm35jwrKCzf8jMtsM/view?usp=sharing" // 👈 cambia por tu URL real
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl))
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEDE430),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Política de Privacidad")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para ver Términos
        Button(
            onClick = {
                val pdfUrl = "https://drive.google.com/file/d/1GZTq17LzE-Oa23Eti5YO94tfSKoRqYAA/view?usp=sharing" // 👈 cambia por tu URL real
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl))
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEDE430),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Términos y Condiciones")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para volver
        Text(
            text = "Volver",
            modifier = Modifier
                .clickable { navController.popBackStack() }
                .padding(8.dp),
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}
