package com.igonris.features.home.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.igonris.common.base.BaseFragment
import com.igonris.features.R
import com.igonris.features.databinding.FragmentHomeBinding
import com.igonris.features.home.ui.adapter.HomeListAdapter
import com.igonris.features.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val viewModel by viewModels<HomeViewModel>()
    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var pokeAdapter: HomeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        (menu.findItem(R.id.search_bar)?.actionView as? SearchView)
            ?.let { searchView ->
                searchView.queryHint = getString(R.string.search_pokemon)

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.filterPokemon(searchView.query.toString())
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.filterPokemon(searchView.query.toString())
                        return false
                    }
                })
            }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentHomeBinding.bind(view)

        configureView()
        setListeners()
    }

    private fun configureView() {
        pokeAdapter = HomeListAdapter { idPokemon, pokeImg, pokeName, cardView ->
            viewModel.onPokemonClick(idPokemon, pokeImg, pokeName, cardView)
        }

        viewBinding.pokeListRV.apply {
            adapter = pokeAdapter
            layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setListeners() {
        viewBinding.pokeListRV.setOnScrollChangeListener { _, _, _, _, _ ->
            viewModel.onScroll(viewBinding.pokeListRV.canScrollVertically(1))
        }

        viewModel.loading.observe(viewLifecycleOwner) { _loading ->
            _loading.doIfUnhandled { loading ->
                if (loading) {
                    viewBinding.loading.show()
                } else {
                    viewBinding.loading.hide()
                }
            }
        }


        viewModel.listData.observe(viewLifecycleOwner) { list ->
            pokeAdapter.setItems(list)
        }
    }
}