package com.example.flightsearch.repository

import com.example.flightsearch.data.local.Airport
import com.example.flightsearch.data.local.AirportDao
import com.example.flightsearch.data.local.Favorite
import com.example.flightsearch.data.local.FavoriteDao
import com.example.flightsearch.ui.model.FlightItem
import com.example.flightsearch.data.preferences.SearchPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class FlightRepository(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao,
    private val prefs: SearchPreferences
) {
    fun searchAirports(query: String): Flow<List<Airport>> =
        airportDao.searchAirports("%$query%")

    fun getDestinations(origin: String): Flow<List<Airport>> =
        airportDao.getDestinations(origin)

    fun getTopAirports(): Flow<List<Airport>> = airportDao.getTopAirports()

    fun getFavorites(): Flow<List<Favorite>> = favoriteDao.getFavorites()

    fun getSavedQuery(): Flow<String> = prefs.searchQueryFlow

    suspend fun saveQuery(query: String) = prefs.saveSearchQuery(query)

    suspend fun addFavorite(dep: String, dest: String) =
        favoriteDao.addFavorite(Favorite(departureCode = dep, destinationCode = dest))

    suspend fun removeFavorite(origin: String, dest: String) =
        favoriteDao.removeFavorite(origin, dest)

    fun getFlights(originCode: String): Flow<List<FlightItem>> {
        val originListFlow  = airportDao.getByIataList(originCode)
        val allAirportsFlow = airportDao.getAll()
        val favsFlow = favoriteDao.getFavorites() // Flow<List<Favorite>>

        return combine(originListFlow, allAirportsFlow, favsFlow) { originList, airports, favs ->
            val origin = originList.firstOrNull()
                ?: return@combine emptyList<FlightItem>()
            val favSet = favs.map { it.departureCode to it.destinationCode }.toSet()
            airports
                .filter { it.iataCode != originCode }
                .map { dest ->
                    FlightItem(
                        departCode = origin.iataCode,
                        departName = origin.name,
                        arriveCode = dest.iataCode,
                        arriveName = dest.name,
                        isFavorite = favSet.contains(originCode to dest.iataCode)
                    )
                }
        }
    }
    fun getFavoriteFlightItems(): Flow<List<FlightItem>> {
        return combine(
            airportDao.getAll(),             // Flow<List<Airport>>
            favoriteDao.getFavorites()       // Flow<List<Favorite>>
        ) { airports, favs ->
            // Создаём мапу код→Airport для быстрого поиска по коду
            val mapByCode = airports.associateBy { it.iataCode }
            favs.mapNotNull { fav ->
                val origin = mapByCode[fav.departureCode]
                val dest   = mapByCode[fav.destinationCode]
                if (origin != null && dest != null) {
                    FlightItem(
                        departCode  = origin.iataCode,
                        departName  = origin.name,
                        arriveCode  = dest.iataCode,
                        arriveName  = dest.name,
                        isFavorite   = true
                    )
                } else null
            }
        }
    }
}