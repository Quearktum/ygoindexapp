package com.example.ygoindexapp.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.coroutines.flow.Flow

@Parcelize
@Entity(tableName = "Cards_table")
data class Card(
    val cardName: String,
    val cardType: String,
    val cardDesc: String,
    val cardRace: String,
    var cardAtk: String?,
    var cardDef: String?,
    var cardLevel: String?,
    var cardAtribute: String?,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
) : Parcelable{}