package com.rickandmorty.entities

data class Character (
        val id: Int?,
        val name: String?,
        val status: String?,
        val species: String?,
        val origin: Origin?,
        val image: String?
)