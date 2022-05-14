package com.example.pokmon.pokemonActivity

import com.example.pokmon.model.Pokemon

interface PokemonView {

    fun intentToFavActivity()

    fun snackbar(message: String, color:Int)

    fun notifyDataSetChanged()

    fun onGetDataSuccessful(pokemons: ArrayList<Pokemon>)

    fun onError(exception: Throwable, message: String = exception.localizedMessage!!)

    fun onEmptyData()

    fun goneProgressBarVisibility()

    fun visibleProgressBarVisibility()

    fun goneRecycleViewVisibility()

    fun visibleRecycleViewVisibility()

    fun goneTextMsgViewVisibility()

    fun visibleTextMsgViewVisibility()
}