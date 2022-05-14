package com.example.pokmon.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_pokemons")
class Pokemon(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    var url: String
)
