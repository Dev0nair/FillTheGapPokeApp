package com.igonris.features.detail.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.igonris.common.types.ErrorType
import com.igonris.features.R
import com.igonris.features.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var viewBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDetailBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.card.background = null

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if(loading) {
                viewBinding.loading.show()
            } else {
                viewBinding.loading.hide()
            }
        }

        viewModel.errors.observe(viewLifecycleOwner) { error ->
            when (error) {
                is ErrorType.APIError -> Log.e("ismael-home", error.desc)
            }
        }

        viewModel.pokeInfo.observe(viewLifecycleOwner) { data ->
            viewBinding.pokeImage.load(data.image)
            viewBinding.pokeName.text = data.name
            viewBinding.abilitiesLv.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, data.abilities)
            viewBinding.typesLv.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, data.types)
        }

        viewModel.loadInfo(arguments?.getInt("pokeId") ?: 0)
    }
}