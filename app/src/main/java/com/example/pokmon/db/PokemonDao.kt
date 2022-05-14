package com.example.pokmon.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokmon.model.Pokemon

@Dao
interface PokemonDao {
    @Insert
    suspend fun insertPokemon(pokemon: Pokemon)

    @Query("delete from fav_pokemons where name=:pokemonName")
    suspend fun deletePokemon(pokemonName: String)

    @Query("select * from fav_pokemons")
    suspend fun getPokemons(): List<Pokemon>
}