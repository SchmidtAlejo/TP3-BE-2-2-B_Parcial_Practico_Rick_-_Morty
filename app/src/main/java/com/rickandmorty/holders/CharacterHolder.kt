package com.rickandmorty.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickandmorty.R
import com.rickandmorty.entities.Character

class CharacterHolder(val view: View): RecyclerView.ViewHolder(view) {

    fun render(item: Character, onClickListener: (Character) -> Unit) {
        val name= view.findViewById<TextView>(R.id.itemCharacterNameId)
        val status= view.findViewById<TextView>(R.id.itemCharacterStatusId)
        val image= view.findViewById<ImageView>(R.id.itemCharacterImageId)
        name.text= item.name
        status.text= item.status

        Glide
            .with(view)
            .load(item.image)
            .centerCrop()
            .into(image)

        view.setOnClickListener {
            onClickListener(item)
        }
    }

}