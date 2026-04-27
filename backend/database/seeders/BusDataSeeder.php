<?php

namespace Database\Seeders;

use App\Models\Bus;
use App\Models\BusRoute;
use Illuminate\Database\Seeder;

class BusDataSeeder extends Seeder
{
    public function run(): void
    {
        $routes = [
            [
                'name' => 'Hamacas - Carrera 33',
                'color' => '#E1131F',
                'points' => [
                    ['lat' => 7.156542781409377, 'lng' => -73.13735090196793],
                    ['lat' => 7.156361812079029, 'lng' => -73.13802681863876],
                    ['lat' => 7.156127616368512, 'lng' => -73.13799463213063],
                    ['lat' => 7.155755032009696, 'lng' => -73.13776932656906],
                    ['lat' => 7.155031153008251, 'lng' => -73.13726507127494],
                    ['lat' => 7.154275336943341, 'lng' => -73.13721142709471],
                    ['lat' => 7.153679199445949, 'lng' => -73.13696466386568],
                ],
                'bus' => [
                    'name' => 'Hamacas Bus',
                    'driver_name' => 'Carlos Pérez',
                    'plate' => 'TMB-101',
                    'passenger_count' => 30,
                ]
            ],
            [
                'name' => 'Caracolí - Carrera 33',
                'color' => '#FFD700',
                'points' => [
                    ['lat' => 7.0715176814219065, 'lng' => -73.10564515407367],
                    ['lat' => 7.072157540373758, 'lng' => -73.10640519639948],
                    ['lat' => 7.072832624976801, 'lng' => -73.10706039477455],
                    ['lat' => 7.074323311363237, 'lng' => -73.10787724024712],
                ],
                'bus' => [
                    'name' => 'Caracolí Bus',
                    'driver_name' => 'María Gómez',
                    'plate' => 'DEF-456',
                    'passenger_count' => 25,
                ]
            ],
            [
                'name' => 'Cumbre - Carrera 33',
                'color' => '#0033CC',
                'points' => [
                    ['lat' => 7.0771789754590895, 'lng' => -73.08927174767031],
                    ['lat' => 7.07854013778212, 'lng' => -73.08804149625405],
                    ['lat' => 7.0783531903730355, 'lng' => -73.08781356298478],
                    ['lat' => 7.079806094638653, 'lng' => -73.08672857644231],
                ],
                'bus' => [
                    'name' => 'Cumbre Bus',
                    'driver_name' => 'Luis Díaz',
                    'plate' => 'TMB-303',
                    'passenger_count' => 30,
                ]
            ]
        ];

        foreach ($routes as $routeData) {
            $route = BusRoute::create([
                'name' => $routeData['name'],
                'color' => $routeData['color'],
                'points' => json_encode($routeData['points']),
            ]);

            Bus::create([
                'name' => $routeData['bus']['name'],
                'bus_route_id' => $route->id,
                'driver_name' => $routeData['bus']['driver_name'],
                'plate' => $routeData['bus']['plate'],
                'current_lat' => $routeData['points'][0]['lat'],
                'current_lng' => $routeData['points'][0]['lng'],
                'passenger_count' => $routeData['bus']['passenger_count'],
            ]);
        }
    }
}
