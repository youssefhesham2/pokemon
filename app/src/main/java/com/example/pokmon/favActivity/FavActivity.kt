package com.example.pokmon.favActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pokmon.R
import com.example.pokmon.adapters.FavAdapter
import com.example.pokmon.databinding.ActivityFavBinding
import com.example.pokmon.model.Pokemon
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavActivity : AppCompatActivity(), FavView {
    private lateinit var binding: ActivityFavBinding

    @Inject
    lateinit var favAdapter: FavAdapter

    @Inject
    lateinit var favPresenter: FavPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favPresenter.setView(this)
        favPresenter.favRepository.setDataProviderListener(favPresenter)
        initAdapter()
        setupSwipe()
        getFavPokemons()
    }

    private fun initAdapter() {
        binding.rvFavPokemon.adapter = favAdapter
    }

    private fun getFavPokemons() {
        favPresenter.getFavPokemons()
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
                val swipedPokemon = favAdapter.getPokemon(swipedPokemonPosition)
                favPresenter.onItemSwipe(swipedPokemon.name)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvFavPokemon)
    }

    override fun snackbar(message: String, color: Int) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(this, color))
            .show()
    }

    override fun notifyDataSetChanged() {
        favAdapter.notifyDataSetChanged()
    }

    override fun onGetDataSuccessful(pokemons: List<Pokemon>) {
        favAdapter.setPokemon(pokemons as ArrayList<Pokemon>)
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
        binding.rvFavPokemon.visibility = View.GONE
    }

    override fun visibleRecycleViewVisibility() {
        binding.rvFavPokemon.visibility = View.VISIBLE
    }

    override fun goneTextMsgViewVisibility() {
        binding.tvMsg.visibility = View.GONE
    }

    override fun visibleTextMsgViewVisibility() {
        binding.tvMsg.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        favPresenter.onDestroy()
        super.onDestroy()
    }
}