package com.example.bumangapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.example.bumangapp.network.RetrofitClient
import com.example.bumangapp.network.SessionManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


data class BusInfo(
    val nombreRuta: String,
    val conductor: String,
    val placa: String,
    val pasajeros: Int
)

class BusViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val gson = Gson()

    // Información de los buses
    private val _busInfoMap = MutableStateFlow<Map<String, BusInfo>>(emptyMap())
    
    // Posiciones actuales de los buses
    private val _busPositions = MutableStateFlow<Map<String, LatLng>>(emptyMap())
    val busPositions: StateFlow<Map<String, LatLng>> = _busPositions

    // Rutas predefinidas
    private val _routes = MutableStateFlow<Map<String, List<LatLng>>>(emptyMap())

    init {
        loadDataFromApi()
    }

    private fun loadDataFromApi() {
        viewModelScope.launch {
            try {
                val token = sessionManager.fetchAuthToken() ?: return@launch
                val authHeader = "Bearer $token"

                val routesResponse = RetrofitClient.instance.getRoutes(authHeader)
                if (routesResponse.isSuccessful && routesResponse.body() != null) {
                    val apiRoutes = routesResponse.body()!!
                    val routesMap = mutableMapOf<String, List<LatLng>>()
                    val infoMap = mutableMapOf<String, BusInfo>()

                    apiRoutes.forEach { route ->
                        val listType = object : TypeToken<List<Map<String, Double>>>() {}.type
                        val points: List<Map<String, Double>> = gson.fromJson(route.points, listType)
                        val latLngList = points.map { LatLng(it["lat"] ?: 0.0, it["lng"] ?: 0.0) }
                        
                        val routeKey = route.id.toString()
                        routesMap[routeKey] = latLngList
                        
                        // Si la ruta tiene buses (la relación with('buses') en Laravel)
                        // Para simplificar, asumiremos que cada ruta tiene al menos un bus en este ejemplo
                        // apiRoutes en el backend tiene with('buses')
                    }
                    _routes.value = routesMap
                    
                    val busesResponse = RetrofitClient.instance.getBuses(authHeader)
                    if (busesResponse.isSuccessful && busesResponse.body() != null) {
                        val apiBuses = busesResponse.body()!!
                        apiBuses.forEach { bus ->
                            val busKey = bus.routeId.toString()
                            infoMap[busKey] = BusInfo(
                                nombreRuta = bus.name,
                                conductor = bus.driverName,
                                placa = bus.plate,
                                pasajeros = bus.passengerCount
                            )
                            
                            // Iniciar simulación para este bus si tiene puntos en la ruta
                            val points = routesMap[busKey]
                            if (points != null && points.size >= 2) {
                                startRouteSimulation(busKey, points)
                            }
                        }
                        _busInfoMap.value = infoMap
                    }
                }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    private fun startRouteSimulation(routeName: String, points: List<LatLng>) {
        viewModelScope.launch {
            var idx = 0
            while (true) {
                val start = points[idx]
                val end = points[(idx + 1) % points.size]

                val steps = 10
                val stepDelay = 2000L // 2 segundos por paso para que se vea mas rapido en pruebas

                for (i in 0..steps) {
                    val t = i / steps.toFloat()
                    val lat = start.latitude + (end.latitude - start.latitude) * t
                    val lng = start.longitude + (end.longitude - start.longitude) * t

                    val current = _busPositions.value.toMutableMap()
                    current[routeName] = LatLng(lat, lng)
                    _busPositions.value = current

                    delay(stepDelay)
                }

                idx = (idx + 1) % points.size
            }
        }
    }


    fun getRoute(routeId: String?): List<LatLng>? = _routes.value[routeId]
    
    fun getBusInfo(routeId: String): BusInfo? = _busInfoMap.value[routeId]
    
    fun getAllRoutes(): Map<String, List<LatLng>> = _routes.value

}
