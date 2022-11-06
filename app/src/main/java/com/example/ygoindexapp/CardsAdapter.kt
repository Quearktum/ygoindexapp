package com.example.ygoindexapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ygoindexapp.data.local.Card
import com.example.ygoindexapp.databinding.CardRowBinding

class CardsAdapter(private val listener: (Card) -> Unit) : RecyclerView.Adapter<CardsAdapter.MyHolder>() {

    private var oldData = emptyList<Card>()

    class MyHolder(val binding: CardRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(CardRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.binding.tvName.text = oldData[position].cardName
        holder.binding.tvRace.text = oldData[position].cardRace

        //click listener for card
        holder.binding.cvCard.setOnClickListener{
            listener(oldData[position])
        }
    }

    override fun getItemCount(): Int {
        return oldData.size
    }

    //set a new instance of data
    fun setData(newData: List<Card>){
        oldData = newData
        notifyDataSetChanged()
    }
}