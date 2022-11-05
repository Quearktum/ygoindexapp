package com.example.ygoindexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ygoindexapp.data.local.Card
import com.example.ygoindexapp.databinding.ActivityDetailedBinding
import com.example.ygoindexapp.databinding.ActivityMainBinding

class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val card = intent.getParcelableExtra<Card>("card")

        card?.let{
            Log.i(null,"${it.cardName} from intent")
            binding.tvNameDetailed.text = it.cardName
        }
    }
}