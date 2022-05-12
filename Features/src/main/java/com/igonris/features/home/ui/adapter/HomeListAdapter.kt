package com.igonris.features.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.igonris.features.databinding.ItemListPokemonBinding
import com.igonris.repository.pokemonrepository.bo.PokemonShortInfoBO

class HomeListAdapter: RecyclerView.Adapter<HomeListAdapter.HomeListHolder>() {

    private val items = ArrayList<PokemonShortInfoBO>()

    inner class HomeListHolder(val viewBinding: ItemListPokemonBinding): RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListHolder {
        val viewBinding = ItemListPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeListHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: HomeListHolder, position: Int) {
        val item = items[position]

        holder.viewBinding.pokeImage.load(item.image)
        holder.viewBinding.pokeName.text = item.name
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(list: List<PokemonShortInfoBO>) {
        val count = itemCount
        items.addAll(items.size, list)
        notifyItemRangeInserted(count, list.size)
    }
}