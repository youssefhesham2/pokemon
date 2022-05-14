package com.example.pokmon.repository

import com.example.pokmon.db.PokemonDao
import com.example.pokmon.utils.DataProviderListener
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class FavRepository @Inject constructor(private val pokemonDao: PokemonDao) {

    private lateinit var dataProviderListener: DataProviderListener

    fun setDataProviderListener(dataProviderListener: DataProviderListener) {
        this.dataProviderListener = dataProviderListener
    }

    suspend fun deleteFavPokemon(name: String) {
        pokemonDao.deletePokemon(name)
    }

    suspend fun getFavPokemons() {
        try {
            val response = pokemonDao.getPokemons()

            response.let {
                if (it.isNotEmpty())
                    dataProviderListener.onSuccess(response)
                else
                    dataProviderListener.onEmpty()
            }
        } catch (exception: Exception) {
            dataProviderListener.onError(exception)
        }
    }
}