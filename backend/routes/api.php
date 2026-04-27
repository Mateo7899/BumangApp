<?php

use App\Http\Controllers\AuthController;
use App\Http\Controllers\BusController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

Route::post('/register', [AuthController::class, 'register']);
Route::post('/login', [AuthController::class, 'login']);

Route::middleware('auth:sanctum')->group(function () {
    Route::get('/user', function (Request $request) {
        return $request->user();
    });
    Route::post('/logout', [AuthController::class, 'logout']);
    Route::put('/user/password', [AuthController::class, 'updatePassword']);
    Route::put('/user/premium', [AuthController::class, 'updatePremium']);

    Route::get('/routes', [BusController::class, 'getRoutes']);
    Route::get('/routes/{id}', [BusController::class, 'getRoute']);
    Route::get('/buses', [BusController::class, 'getBuses']);
});
