package com.example.pokmon.network

import com.example.pokmon.model.PokemonResponse
import retrofit2.http.GET

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemons(): PokemonResponse
}