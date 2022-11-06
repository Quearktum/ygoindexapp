package com.example.ygoindexapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ygoindexapp.data.local.Card
import com.example.ygoindexapp.data.local.CardDatabase
import com.example.ygoindexapp.databinding.ActivityAddBinding
import com.example.ygoindexapp.databinding.ActivityMainBinding

class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get instance of the database
        val cardDb = CardDatabase.getInstance(this)
        val myViewModelFactory = ViewModelFactory(cardDb!!)

        viewModel = ViewModelProvider(this, myViewModelFactory).get(MainViewModel::class.java)

        //Add button functionality
        binding.btAdd.setOnClickListener{
            insertDataToDatabase()
        }
    }

    //insert data to database
    private fun insertDataToDatabase() {

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
            val card = Card(
                name,
                type,
                desc,
                race,
                atk,
                def,
                level,
                attribute
            )
            // Add Data to Database
            viewModel.insertCard(card)
            Toast.makeText(applicationContext, "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate Back

        }else{
            Toast.makeText(applicationContext, "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    //checking if the fields are empty or not
    private fun inputCheck(name: String, type: String, desc: String, race: String): Boolean{
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(type) && TextUtils.isEmpty(desc) && TextUtils.isEmpty(race))
    }

}