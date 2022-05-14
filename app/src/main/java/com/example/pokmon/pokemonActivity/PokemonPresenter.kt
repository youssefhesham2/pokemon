package com.example.pokmon.pokemonActivity

import android.util.Log
import com.example.pokmon.R
import com.example.pokmon.model.Pokemon
import com.example.pokmon.model.PokemonResponse
import com.example.pokmon.repository.PokemonRepository
import com.example.pokmon.utils.DataProviderListener
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ActivityScoped
class PokemonPresenter @Inject constructor(
    val pokemonRepository: PokemonRepository,
    private val coroutineScope: CoroutineScope
) : DataProviderListener {
    private val tag = "PokemonPresenter"
    private var pokemonView: PokemonView? = null

    fun setView(pokemonView: PokemonView) {
        this.pokemonView = pokemonView
    }

    fun getPokemons() {
        coroutineScope.launch {
            try {
                coroutineScope {
                    async(Dispatchers.IO) { pokemonRepository.getPokemons() }.await()
                }
            } catch (exception: Exception) {
                onError(exception)
            }
        }
    }

    fun onItemSwipe(pokemon: Pokemon) {
        coroutineScope.launch {
            try {
                coroutineScope {
                    withContext(Dispatchers.IO) { pokemonRepository.insertFavPokemon(pokemon) }
                    pokemonView?.snackbar(
                        "Pokemon Added to Favourite list successfully",
                        R.color.successful
                    )
                }
            } catch (exception: Exception) {
                pokemonView?.snackbar(exception.message.toString(), R.color.error)
            } finally {
                pokemonView?.notifyDataSetChanged()
            }
        }
    }

    fun favButtonClick() {
        pokemonView?.intentToFavActivity()
    }

    override fun onSuccess(result: Any) {
        val pokemonResponse = (result as PokemonResponse)
        coroutineScope.launch {
            try {
                flow<Pokemon> {
                    for (i in pokemonResponse.results) {
                        emit(i)
                    }
                }.buffer().onCompletion {
                    pokemonView?.goneProgressBarVisibility()
                    pokemonView?.goneTextMsgViewVisibility()
                    pokemonView?.visibleRecycleViewVisibility()
                    pokemonView?.onGetDataSuccessful(pokemonResponse.results)
                }
                    .collect {
                        val url: String = it.url
                        val pokemonIndex = url.split("/")
                        it.url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${
                                pokemonIndex[pokemonIndex.size - 2]
                            }.png"
                    }
            } catch (exception: Exception) {
                onError(exception)
            }
        }
    }

    override fun onEmpty() {
        coroutineScope.launch {
            pokemonView?.goneProgressBarVisibility()
            pokemonView?.goneRecycleViewVisibility()
            pokemonView?.visibleTextMsgViewVisibility()
            pokemonView?.onEmptyData()
        }
    }

    override fun onError(exception: Throwable, message: String) {
        coroutineScope.launch {
            Log.e(tag, message)
            pokemonView?.goneProgressBarVisibility()
            pokemonView?.goneRecycleViewVisibility()
            pokemonView?.visibleTextMsgViewVisibility()
            pokemonView?.onError(exception)
        }
    }

    fun onDestroy() {
        coroutineScope.cancel()
        pokemonView = null
    }
}