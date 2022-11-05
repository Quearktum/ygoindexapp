package com.example.ygoindexapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import androidx.room.Query

@Dao
interface cardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Card)

    @Delete
    suspend fun deleteCard(card: Card)

    @Update
    suspend fun updateCard(card: Card)

    @Query("SELECT * FROM Cards_table ORDER BY id ASC")
    fun listCards(): Flow<List<Card>>

    @Query("SELECT * FROM Cards_table WHERE cardName LIKE :searchQuery")
    fun searchCardsByName(searchQuery: String): Flow<List<Card>>
}