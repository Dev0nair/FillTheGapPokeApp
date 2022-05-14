package com.igonris.features.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.igonris.features.databinding.ItemListPokemonBinding
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO

class HomeListAdapter(
    private val onItemClick: (Int) -> Unit
): RecyclerView.Adapter<HomeListAdapter.HomeListHolder>() {

    private val items = ArrayList<PokemonShortInfoBO>()

    inner class HomeListHolder(val viewBinding: ItemListPokemonBinding): RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListHolder {
        val viewBinding = ItemListPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeListHolder(viewBinding)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: HomeListHolder, position: Int) {
        val item = items[position]

        holder.viewBinding.pokeImage.load(item.image)
        holder.viewBinding.pokeName.text = item.name

        holder.viewBinding.root.setOnClickListener {
            onItemClick(position+1)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItems() = items

    fun setItems(list: List<PokemonShortInfoBO>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun addItems(list: List<PokemonShortInfoBO>) {
        val count = items.size
        val notExistingData = list.filterNot { pokemon ->
            items.any { poke -> poke.name.equals(pokemon) }
        }
        items.addAll(items.size, notExistingData)
        notifyItemRangeInserted(count, notExistingData.size)
    }

    fun clear() {
        val count = items.size
        items.clear()
        notifyItemRangeRemoved(0, count)
    }
}