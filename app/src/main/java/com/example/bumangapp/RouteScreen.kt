package com.example.bumangapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

//Modelo de datos adaptado para la API
data class RouteItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val color: Color
)

@Composable
fun RouteScreen(navController: NavHostController, busViewModel: BusViewModel ) {
    val currentRoute = "route"
    var searchQuery by remember { mutableStateOf("") }
    
    // Obtenemos las rutas del ViewModel (que vienen de la API)
    val apiRoutes = busViewModel.getAllRoutes()
    
    // Mapeamos a RouteItem para la UI
    val routes = apiRoutes.map { (id, points) ->
        val info = busViewModel.getBusInfo(id)
        RouteItem(
            id = id,
            title = info?.nombreRuta?.uppercase() ?: "RUTA $id",
            subtitle = "CONDUCTOR: ${info?.conductor ?: "N/A"}",
            description = "PLACA: ${info?.placa ?: "N/A"}",
            color = if (id.toIntOrNull()?.rem(2) == 0) Color(0xFFCF1A17) else Color(0xFFEDE430)
        )
    }

    // Filtrar rutas según la búsqueda
    val filteredRoutes = routes.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.subtitle.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Rutas",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
        )

        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Buscar ruta...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de rutas
        if (filteredRoutes.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("Cargando rutas o no se encontraron resultados...")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                items(filteredRoutes) { route ->
                    RouteItemCard(route = route) { routeId ->
                        navController.navigate("route_map/$routeId")
                    }
                }
            }
        }

        //Barra inferior
        BottomBar(navController = navController, currentRoute = currentRoute)
    }
}

//Tarjeta individual de ruta
@Composable
fun RouteItemCard(
    route: RouteItem,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick(route.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            // Encabezado del cartel
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(route.color)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CARTEL DE RUTA IDA",
                    color = if (route.color == Color(0xFFEDE430)) Color.Black else Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "MoveBGA",
                    color = if (route.color == Color(0xFFEDE430)) Color.Black else Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            // Detalle de la ruta
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsBus,
                    contentDescription = "Bus",
                    tint = route.color,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 12.dp)
                )

                Column {
                    Text(
                        text = route.title,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = route.subtitle,
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = route.description,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
