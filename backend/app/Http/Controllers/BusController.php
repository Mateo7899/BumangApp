<?php

namespace App\Http\Controllers;

use App\Models\Bus;
use App\Models\BusRoute;
use Illuminate\Http\Request;

class BusController extends Controller
{
    public function getRoutes()
    {
        return response()->json(BusRoute::with('buses')->get());
    }

    public function getBuses()
    {
        return response()->json(Bus::with('route')->get());
    }

    public function getRoute($id)
    {
        return response()->json(BusRoute::with('buses')->findOrFail($id));
    }
}
