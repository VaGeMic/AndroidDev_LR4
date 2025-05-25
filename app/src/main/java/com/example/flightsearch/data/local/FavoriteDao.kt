// app/src/main/java/com/example/flightsearch/data/local/FavoriteDao.kt
package com.example.flightsearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    /**
     * Вставляет новый маршрут в таблицу.
     * В случае конфликта (такой же origin+destination) — игнорируем.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE departure_code = :dep AND destination_code = :dest")
    suspend fun removeFavorite(dep: String, dest: String): Int

    // Список всех избранных
    @Query("SELECT * FROM favorite")
    fun getFavorites(): Flow<List<Favorite>>
}
