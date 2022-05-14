package com.example.pokmon.di

import android.app.Application
import androidx.room.Room
import com.example.pokmon.db.PokemonDB
import com.example.pokmon.db.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun providePokemonDB(application: Application): PokemonDB {
        return Room.databaseBuilder(application, PokemonDB::class.java, "fav_DB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(pokemonDB: PokemonDB): PokemonDao {
        return pokemonDB.pokemonDao()
    }
}