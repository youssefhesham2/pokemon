package com.example.pokmon.favActivity

import android.util.Log
import com.example.pokmon.R
import com.example.pokmon.model.Pokemon
import com.example.pokmon.repository.FavRepository
import com.example.pokmon.utils.DataProviderListener
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.*
import javax.inject.Inject

@ActivityScoped
class FavPresenter @Inject constructor(
    val favRepository: FavRepository,
    private val coroutineScope: CoroutineScope
) : DataProviderListener {
    private val tag = "FavPresenter"
    private var favView: FavView?=null

    fun setView(favView: FavView) {
        this.favView = favView
    }

    fun getFavPokemons() {
        coroutineScope.launch {
            try {
                coroutineScope {
                    async(Dispatchers.IO) { favRepository.getFavPokemons() }.await()
                }
            } catch (exception: Exception) {
                onError(exception)
            }
        }
    }

    fun onItemSwipe(pokemonName: String) {
        coroutineScope.launch {
            try {
                coroutineScope {
                    withContext(Dispatchers.IO) { favRepository.deleteFavPokemon(pokemonName) }
                    favView?.snackbar(
                        "Pokemon Deleted from Favourite list successfully",
                        R.color.successful
                    )
                    getFavPokemons()
                }
            } catch (exception: Exception) {
                favView?.snackbar(exception.message.toString(), R.color.error)
                favView?.notifyDataSetChanged()
            }
        }
    }

    override fun onSuccess(result: Any) {
        coroutineScope.launch {
            favView?.goneProgressBarVisibility()
            favView?.goneTextMsgViewVisibility()
            favView?.visibleRecycleViewVisibility()
            favView?.onGetDataSuccessful(result as List<Pokemon>)
        }
    }

    override fun onEmpty() {
        coroutineScope.launch {
            favView?.goneProgressBarVisibility()
            favView?.goneRecycleViewVisibility()
            favView?.visibleTextMsgViewVisibility()
            favView?.onEmptyData()
        }
    }

    override fun onError(exception: Throwable, message: String) {
        coroutineScope.launch {
            Log.e(tag, message)
            favView?.goneProgressBarVisibility()
            favView?.goneRecycleViewVisibility()
            favView?.visibleTextMsgViewVisibility()
            favView?.onError(exception)
        }
    }

    fun onDestroy() {
        coroutineScope.cancel()
        favView=null
    }
}