package com.rickandmorty.responses

import com.rickandmorty.entities.Character

data class GetAllCharacterResponse (
        val results: List<Character>
)