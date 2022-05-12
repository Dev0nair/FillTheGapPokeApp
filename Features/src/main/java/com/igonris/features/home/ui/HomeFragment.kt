package com.igonris.features.home.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.igonris.common.ErrorType
import com.igonris.common.ResultType
import com.igonris.features.R
import com.igonris.features.databinding.FragmentHomeBinding
import com.igonris.features.home.ui.adapter.HomeListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var pokeAdapter: HomeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureView()
        setListeners()
        viewModel.getListData()
    }

    private fun configureView() {
        pokeAdapter = HomeListAdapter()
        viewBinding.pokeListRV.apply {
            adapter = pokeAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
    }

    private fun setListeners() {
        viewBinding.pokeListRV.setOnScrollChangeListener { _, _, _, _, _ ->
            if(pokeAdapter.itemCount > 0 && !viewBinding.pokeListRV.canScrollVertically(0)) {
                viewModel.getListData()
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if(loading) {
                viewBinding.loading.show()
            } else {
                viewBinding.loading.hide()
            }
        }

        viewModel.errors.observe(viewLifecycleOwner) { error ->
            when(error) {
                is ErrorType.APIError -> Log.e("ismael-home", error.desc)
            }

        }

        viewModel.listData.observe(viewLifecycleOwner) { list ->
            pokeAdapter.addItems(list)
        }
    }
}