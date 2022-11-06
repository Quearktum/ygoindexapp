package com.example.ygoindexapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ygoindexapp.data.local.Card
import com.example.ygoindexapp.data.local.CardDatabase
import com.example.ygoindexapp.databinding.ActivityMainBinding
import com.example.ygoindexapp.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get instance of the database
        val cardDb = CardDatabase.getInstance(this)
        val myViewModelFactory = ViewModelFactory(cardDb!!)

        viewModel = ViewModelProvider(this, myViewModelFactory).get(MainViewModel::class.java)

        val card = intent.getParcelableExtra<Card>("card")

        card?.let{
            binding.etName.setText(it.cardName)
            binding.etType.setText(it.cardType)
            binding.etLevel.setText("${it.cardLevel}")
            binding.etAttribute.setText("${it.cardAtribute}")
            binding.etAtk.setText("${it.cardAtk}")
            binding.etDef.setText("${it.cardDef}")
            binding.etRace.setText(it.cardRace)
            binding.etDesc.setText(it.cardDesc)
        }

        binding.btUpdate.setOnClickListener{
            if (card != null) {
                updateDataToDatabase(card.id)
            }
        }

    }

    //Update Card functionality
    private fun updateDataToDatabase(id:Int) {

        val name = binding.etName.text.toString()
        val type = binding.etType.text.toString()
        val desc = binding.etDesc.text.toString()
        val race = binding.etRace.text.toString()
        val level = binding.etLevel.text.toString()
        val attribute = binding.etAttribute.text.toString()
        val atk = binding.etAtk.text.toString()
        val def = binding.etDef.text.toString()

        if(inputCheck(name, type, desc, race)){
            // Create User Object
            val updatedCard = Card(name,type,desc,race,atk,def,level,attribute,id)
            // Add Data to Database
                viewModel.updateCard(updatedCard)
            Toast.makeText(applicationContext, "Successfully updated!", Toast.LENGTH_LONG).show()
            // Navigate Back
            startActivity(Intent(this,MainActivity::class.java))
        }else{
            Toast.makeText(applicationContext, "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    //Checking if the input is empty or not
    private fun inputCheck(name: String, type: String, desc: String, race: String): Boolean{
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(type) && TextUtils.isEmpty(desc) && TextUtils.isEmpty(race))
    }

}