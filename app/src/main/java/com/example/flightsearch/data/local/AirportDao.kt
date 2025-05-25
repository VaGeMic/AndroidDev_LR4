package com.example.flightsearch.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query(
        "SELECT * FROM airport WHERE iata_code LIKE :query OR name LIKE :query " +
                "ORDER BY CASE WHEN iata_code LIKE :query THEN 0 ELSE 1 END, passengers DESC"
    )
    fun searchAirports(query: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code != :origin ORDER BY iata_code")
    fun getDestinations(origin: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport ORDER BY passengers DESC LIMIT 5")
    fun getTopAirports(): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code = :code")
    fun getByIata(code: String): Flow<Airport>

    @Query("SELECT * FROM airport WHERE iata_code = :code")
    fun getByIataList(code: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport")
    fun getAll(): Flow<List<Airport>>
}