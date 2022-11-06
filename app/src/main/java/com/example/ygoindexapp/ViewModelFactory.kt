package com.example.ygoindexapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ygoindexapp.data.local.CardDatabase

class ViewModelFactory constructor(private val cardDb: CardDatabase): ViewModelProvider.Factory {

    //Factory to generate instance of ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(cardDb) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}