package com.example.bumangapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bumangapp.network.RetrofitClient
import com.example.bumangapp.network.SessionManager
import com.example.bumangapp.ui.theme.BumangRed
import com.example.bumangapp.ui.theme.BumangWhite
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun RegisterScreen(
    onClickBack: () -> Unit = {},
    onSuccessfulRegister: () -> Unit = {}
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val scope = rememberCoroutineScope()

    var inputName by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var inputPasswordConfirmation by remember { mutableStateOf("") }

    var registerError by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onClickBack, modifier = Modifier.align(Alignment.CenterStart)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.Black)
                }
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_bus),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(100.dp))
                    .padding(20.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "BumangApp",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )

            Text(
                text = "REGISTRO",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = inputName,
                onValueChange = { inputName = it },
                placeholder = { Text("Nombre", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(0.9f).height(56.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE0E0E0),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = inputEmail,
                onValueChange = { inputEmail = it },
                placeholder = { Text("Correo electrónico", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(0.9f).height(56.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE0E0E0),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = inputPassword,
                onValueChange = { inputPassword = it },
                placeholder = { Text("Contraseña", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(0.9f).height(56.dp),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null, tint = Color.Black)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE0E0E0),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = inputPasswordConfirmation,
                onValueChange = { inputPasswordConfirmation = it },
                placeholder = { Text("Confirmar contraseña", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(0.9f).height(56.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE0E0E0),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )

            if (registerError.isNotEmpty()){
                Spacer(modifier = Modifier.height(8.dp))
                Text(registerError, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val n = inputName.trim()
                    val e = inputEmail.trim()
                    val p = inputPassword.trim()
                    val c = inputPasswordConfirmation.trim()
                    if (n.isNotEmpty() && e.isNotEmpty() && p.isNotEmpty() && c == p) {
                        isLoading = true
                        scope.launch {
                            try {
                                val response = RetrofitClient.instance.register(mapOf(
                                    "name" to n, "email" to e, "password" to p, "password_confirmation" to c
                                ))
                                if (response.isSuccessful) {
                                    val auth = response.body()!!
                                    sessionManager.saveAuthToken(auth.accessToken)
                                    sessionManager.saveUser(auth.user)
                                    onSuccessfulRegister()
                                } else {
                                    registerError = "Error al registrar"
                                }
                            } catch (err: Exception) {
                                registerError = "Error de conexión"
                            } finally {
                                isLoading = false
                            }
                        }
                    } else {
                        registerError = "Completa todos los campos correctamente"
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(0.8f).height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BumangRed)
            ) {
                Text(if (isLoading) "CARGANDO..." else "REGISTRARSE", fontWeight = FontWeight.Bold, color = BumangWhite)
            }
        }
    }
}
