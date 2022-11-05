package com.example.ygoindexapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ygoindexapp.data.local.Card
import com.example.ygoindexapp.data.local.CardDatabase
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData

class MainViewModel constructor(private val cardDb: CardDatabase) : ViewModel() {

    val cards = cardDb.dao.listCards().asLiveData()

    fun insertCard(card: Card){
        viewModelScope.launch{
            cardDb.dao.insertCard(card)
        }
    }

    fun searchCardByName(searchQuery: String):LiveData<List<Card>>{
        return cardDb.dao.searchCardsByName(searchQuery).asLiveData()
    }
}