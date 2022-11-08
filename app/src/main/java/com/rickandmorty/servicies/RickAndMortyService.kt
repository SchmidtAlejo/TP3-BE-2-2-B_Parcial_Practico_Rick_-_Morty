package com.rickandmorty.servicies

import com.google.gson.JsonArray
import com.rickandmorty.responses.GetAllCharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url
import com.rickandmorty.entities.Character

interface RickAndMortyService {

    @GET
    suspend fun getAllCharacters(@Url url: String): Response<GetAllCharacterResponse>

    @GET
    suspend fun getCharacterById(@Url url: String): Response<Character>

    @GET
    suspend fun getMultipliesCharacter(@Url url: String): Response<JsonArray>

}