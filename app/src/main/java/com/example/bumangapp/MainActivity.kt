package com.example.bumangapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bumangapp.network.SessionManager
import com.example.bumangapp.ui.theme.BumangAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val sessionManager = remember { SessionManager(context) }
            
            BumangAppTheme {
                val navController = rememberNavController()
                val busViewModel: BusViewModel = viewModel()
                
                val startDestination = if (sessionManager.fetchAuthToken() != null) {
                    "map"
                } else {
                    "welcome"
                }

                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        // Pantalla de bienvenida
                        composable("welcome") {
                            WelcomeScreen(
                                onLoginClick = { navController.navigate("login") },
                                onRegisterClick = { navController.navigate("register") }
                            )
                        }
                        // Pantalla de login
                        composable("login") {
                            LoginScreen(
                                onClickRegister = { navController.navigate("register") },
                                onSuccessfulLogin = {
                                    navController.navigate("map") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }
                        // Pantalla de registro
                        composable("register") {
                            RegisterScreen(
                                onClickBack = { navController.popBackStack() },
                                onSuccessfulRegister = {
                                    navController.navigate("login") {
                                        popUpTo("register") { inclusive = true }
                                    }
                                }
                            )
                        }
                        // Pantalla principal: buses moviéndose
                        composable("map") {
                            MapScreen(navController, busViewModel = busViewModel)
                        }
                        // Pantalla de listado de rutas
                        composable("route") {
                            RouteScreen(navController = navController, busViewModel = busViewModel)
                        }
                        // Pantalla de mapa individual de ruta
                        composable(
                            route = "route_map/{routeId}",
                            arguments = listOf(
                                navArgument("routeId") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val routeId = backStackEntry.arguments?.getString("routeId") ?: ""
                            RouteMapScreen(navController, routeId = routeId, busViewModel = busViewModel)
                        }
                        // Pantalla de configuración
                        composable("settings") {
                            SettingsScreen(navController)
                        }
                        // Pantalla de cambio de contraseña
                        composable("change_password") {
                            ChangePasswordScreen(navController)
                        }
                        // Pantalla de Tamaño de texto
                        composable("text_size") {
                            TextSizeScreen(navController)
                        }
                        // Pantalla de privacidad
                        composable("privacy") {
                            PrivacyScreen(navController)
                        }
                        // Pantalla Premium
                        composable("premium") {
                            PremiumScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
