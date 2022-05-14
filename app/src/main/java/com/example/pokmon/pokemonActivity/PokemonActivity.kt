package com.example.pokmon.pokemonActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pokmon.R
import com.example.pokmon.adapters.PokemonAdapter
import com.example.pokmon.databinding.ActivityMainBinding
import com.example.pokmon.favActivity.FavActivity
import com.example.pokmon.model.Pokemon
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PokemonActivity : AppCompatActivity(), PokemonView {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var pokemonAdapter: PokemonAdapter

    @Inject
    lateinit var pokemonPresenter: PokemonPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokemonPresenter.setView(this)
        pokemonPresenter.pokemonRepository.setDataProviderListener(pokemonPresenter)
        initAdapter()
        setupSwipe()
        getPokemons()
        onFavButtonClick()
    }

    private fun initAdapter() {
        binding.rvPokemon.adapter = pokemonAdapter
    }

    private fun getPokemons() {
        pokemonPresenter.getPokemons()
    }

    private fun setupSwipe() {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedPokemonPosition = viewHolder.adapterPosition
                val swipedPokemon = pokemonAdapter.getPokemon(swipedPokemonPosition)
                pokemonPresenter.onItemSwipe(swipedPokemon)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvPokemon)
    }

    private fun onFavButtonClick() {
        binding.fabFavourite.setOnClickListener {
            pokemonPresenter.favButtonClick()
        }
    }

    override fun intentToFavActivity() {
        val intent = Intent(this, FavActivity::class.java)
        startActivity(intent)
    }

    override fun snackbar(message: String, color: Int) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(this, color))
            .show()
    }

    override fun notifyDataSetChanged() {
        pokemonAdapter.notifyDataSetChanged()
    }

    override fun onGetDataSuccessful(pokemons: ArrayList<Pokemon>) {
        pokemonAdapter.setPokemon(pokemons)
    }

    override fun onError(exception: Throwable, message: String) {
        binding.tvMsg.text = message
    }

    override fun onEmptyData() {
        binding.tvMsg.setText(R.string.empty_data)
    }

    override fun goneProgressBarVisibility() {
        binding.progressBar.visibility = View.GONE
    }

    override fun visibleProgressBarVisibility() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun goneRecycleViewVisibility() {
        binding.rvPokemon.visibility = View.GONE
    }

    override fun visibleRecycleViewVisibility() {
        binding.rvPokemon.visibility = View.VISIBLE
    }

    override fun goneTextMsgViewVisibility() {
        binding.tvMsg.visibility = View.GONE
    }

    override fun visibleTextMsgViewVisibility() {
        binding.tvMsg.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        pokemonPresenter.onDestroy()
        super.onDestroy()
    }
}