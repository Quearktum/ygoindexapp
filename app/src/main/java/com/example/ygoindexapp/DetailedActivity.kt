package com.example.ygoindexapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.ygoindexapp.data.local.Card
import com.example.ygoindexapp.data.local.CardDatabase
import com.example.ygoindexapp.databinding.ActivityDetailedBinding
import com.example.ygoindexapp.databinding.ActivityMainBinding

class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get instance of the database
        val cardDb = CardDatabase.getInstance(this)
        val myViewModelFactory = ViewModelFactory(cardDb!!)

        viewModel = ViewModelProvider(this, myViewModelFactory).get(MainViewModel::class.java)

        val card = intent.getParcelableExtra<Card>("card")

        //parse in the taken card's details to the activity elements.
        card?.let {

            var lvType = "Level"
            if (it.cardType.contains("Link"))
                lvType = "Link rating"
            else if (it.cardType.contains("XYZ"))
                lvType = "Rank"

            binding.tvNameDetailed.text = it.cardName
            binding.tvRaceDetailed.text = "${it.cardRace} / ${it.cardType}"
            binding.tvDescDetailed.text = it.cardDesc

            if (it.cardType.contains("Monster")) {
                binding.tvLvlDetailed.text = "$lvType: ${it.cardLevel} ${it.cardAtribute}"
                binding.tvAtkDetailed.text = "ATK/${it.cardAtk}    DEF/${it.cardDef}"

            }

        }

        //Update button functionality
        binding.btUpdate.setOnClickListener {
            intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("card", card)

            startActivity(intent)
        }

        //Delete button functionality
        binding.btDelete.setOnClickListener {
            if (card != null) {
                viewModel.deleteCard(card)
            }

            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}