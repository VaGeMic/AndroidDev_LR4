package com.example.flightsearch.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class SearchPreferences(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val SEARCH_QUERY_KEY = stringPreferencesKey("search_query")
    }

    val searchQueryFlow: Flow<String> = dataStore.data
        .map { prefs -> prefs[SEARCH_QUERY_KEY] ?: "" }

    suspend fun saveSearchQuery(query: String) {
        dataStore.edit { prefs -> prefs[SEARCH_QUERY_KEY] = query }
    }
}