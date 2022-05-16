package com.igonris.features.detail.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.igonris.common.base.BaseFragment
import com.igonris.common.base.BasicListView
import com.igonris.common.extensions.firstUpperThenLower
import com.igonris.features.R
import com.igonris.features.databinding.FragmentDetailBinding
import com.igonris.features.detail.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    override val viewModel: DetailViewModel by viewModels()
    private lateinit var viewBinding: FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentDetailBinding.bind(view)

        viewModel.loading.observe(viewLifecycleOwner) { _loading ->
            _loading.doIfUnhandled { loading ->
                if (loading) {
                    viewBinding.loading.show()
                } else {
                    viewBinding.loading.hide()
                }
            }
        }

        viewModel.pokeInfo.observe(viewLifecycleOwner) { data ->
            viewBinding.pokeImage.load(data.image)

            viewBinding.pokeName.text =
                getString(R.string.name_number, data.name.firstUpperThenLower(), data.id.toString())
            viewBinding.abilitiesLv.run {
                adapter = BasicListView(data.abilities)
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.VERTICAL
                }
            }
            viewBinding.typesLv.run {
                adapter = BasicListView(data.types)
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.VERTICAL
                }
            }

        }

        viewModel.loadInfo(arguments?.getInt("pokeId") ?: 0)
    }
}