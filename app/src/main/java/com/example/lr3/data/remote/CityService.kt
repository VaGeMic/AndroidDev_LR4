package com.example.lr3.data.remote

import com.example.lr3.data.model.CityResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CityService {
    @GET("city")
    suspend fun getCity(
        @Header("X-Api-Key") apiKey: String,
        @Query("name") cityName: String
    ): List<CityResponse>
}
