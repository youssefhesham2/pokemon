package com.example.pokmon.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokmon.R
import com.example.pokmon.model.Pokemon
import com.example.pokmon.pokemonActivity.PokemonPresenter

class PokemonAdapter(private val mContext: Context, var pokemonPresenter: PokemonPresenter) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    private var pokemonList = ArrayList<Pokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon: Pokemon = pokemonList[position]

        holder.pokemonTextView.text = pokemon.name
        Glide.with(mContext).load(pokemon.url).into(holder.pokemonImage)

        holder.itemView.setOnClickListener(View.OnClickListener {

        })
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun setPokemon(list: ArrayList<Pokemon>) {
        pokemonList = list
        notifyDataSetChanged()
    }

    fun getPokemon(position: Int): Pokemon {
        return pokemonList[position]
    }

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonImage:ImageView = itemView.findViewById(R.id.img_pokemon)
        val pokemonTextView:TextView = itemView.findViewById(R.id.tv_pokemon_name)
    }
}