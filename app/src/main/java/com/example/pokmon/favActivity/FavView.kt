package com.example.pokmon.favActivity

import com.example.pokmon.model.Pokemon

interface FavView {

    fun snackbar(message: String, color: Int)

    fun notifyDataSetChanged()

    fun onGetDataSuccessful(pokemons: List<Pokemon>)

    fun onError(exception: Throwable, message: String = exception.localizedMessage!!)

    fun onEmptyData()

    fun goneProgressBarVisibility()

    fun visibleProgressBarVisibility()

    fun goneRecycleViewVisibility()

    fun visibleRecycleViewVisibility()

    fun goneTextMsgViewVisibility()

    fun visibleTextMsgViewVisibility()
}