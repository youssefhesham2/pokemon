package com.example.pokmon.model

class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: ArrayList<Pokemon>
)