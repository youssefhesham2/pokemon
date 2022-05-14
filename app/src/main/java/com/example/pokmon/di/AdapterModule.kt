package com.example.pokmon.di

import android.app.Activity
import com.example.pokmon.adapters.FavAdapter
import com.example.pokmon.adapters.PokemonAdapter
import com.example.pokmon.pokemonActivity.PokemonPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class AdapterModule {

    @Provides
    @ActivityScoped
    fun pokemonAdapterProvide(
        activity: Activity,
        pokemonPresenter: PokemonPresenter
    ): PokemonAdapter {
        return PokemonAdapter(activity, pokemonPresenter)
    }

    @Provides
    @ActivityScoped
    fun favAdapterProvide(activity: Activity): FavAdapter {
        return FavAdapter(activity)
    }

}