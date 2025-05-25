package com.example.flightsearch.ui.model

data class FlightItem(
    val departCode: String,
    val departName: String,
    val arriveCode: String,
    val arriveName: String,
    val isFavorite: Boolean
)