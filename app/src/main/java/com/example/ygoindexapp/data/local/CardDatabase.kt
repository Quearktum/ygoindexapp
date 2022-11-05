package com.example.ygoindexapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ygoindexapp.PrepopulateCard

@Database(entities = [Card::class], version = 1)
abstract class CardDatabase : RoomDatabase() {
    abstract val dao: cardDao

    companion object {
        @Volatile
        private var instance: CardDatabase? = null

        fun getInstance(context: Context): CardDatabase? {
            if (instance == null) {
                synchronized(CardDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CardDatabase::class.java,
                        "cards"
                    )
                        .addCallback(PrepopulateCard(context))
                        .build()
                }
            }
            return instance
        }
    }
}