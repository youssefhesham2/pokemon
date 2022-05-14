package com.example.pokmon.repository

import com.example.pokmon.db.PokemonDao
import com.example.pokmon.model.Pokemon
import com.example.pokmon.network.PokemonApiService
import com.example.pokmon.utils.DataProviderListener
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val pokemonApiService: PokemonApiService,
    private val pokemonDao: PokemonDao
) {

    private lateinit var dataProviderListener: DataProviderListener

    fun setDataProviderListener(dataProviderListener: DataProviderListener) {
        this.dataProviderListener = dataProviderListener
    }

    suspend fun getPokemons() {
        try {
            val response = pokemonApiService.getPokemons()

            response.results.let {
                if (it.isNotEmpty())
                    dataProviderListener.onSuccess(response)
                else
                    dataProviderListener.onEmpty()
            }
        } catch (e: Exception) {
            dataProviderListener.onError(e)
        }
    }

    suspend fun insertFavPokemon(pokemon: Pokemon) {
        pokemonDao.insertPokemon(pokemon)
    }
}