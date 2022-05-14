package com.example.pokmon.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokmon.model.Pokemon

@Database(entities = [Pokemon::class], version = 2, exportSchema = false)
abstract class PokemonDB : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}