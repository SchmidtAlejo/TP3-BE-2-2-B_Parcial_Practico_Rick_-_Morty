package com.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rickandmorty.R
import com.rickandmorty.entities.Character
import com.rickandmorty.holders.CharacterHolder

class CharacterAdapter(
    private val characterList: List<Character>,
    private val onClickListener: (Character)->Unit
) : RecyclerView.Adapter<CharacterHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return CharacterHolder(layoutInflater.inflate(R.layout.item_characther, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val item= characterList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

}
