package com.igonris.features.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.igonris.features.R
import com.igonris.features.databinding.ItemListPokemonBinding
import com.igonris.repository.pokemon.bo.PokemonShortInfoBO

class HomeListAdapter(
    private val onItemClick: (Int, View, View, View) -> Unit
) : RecyclerView.Adapter<HomeListAdapter.HomeListHolder>() {

    private val items = ArrayList<PokemonShortInfoBO>()

    inner class HomeListHolder(val binding: ItemListPokemonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListHolder {
        val binding =
            ItemListPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeListHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeListHolder, position: Int) {
        val item = items[position]

        holder.binding.pokeImage.load(item.image)
        holder.binding.pokeName.text = item.name
        holder.binding.pokeN.text =
            holder.binding.root.context.getString(R.string.number, item.id.toString())

        holder.binding.root.setOnClickListener {
            holder.binding.pokeImage.transitionName = "poke_image_big"
            holder.binding.pokeName.transitionName = "poke_name_big"
            holder.binding.root.transitionName = "poke_view_big"
            onItemClick(
                item.id,
                holder.binding.pokeImage,
                holder.binding.pokeName,
                holder.binding.root
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItems() = items

    fun setItems(list: List<PokemonShortInfoBO>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}