package com.igonris.features.home.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.igonris.common.ErrorType
import com.igonris.common.ResultType
import com.igonris.features.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loading.observe(viewLifecycleOwner) { loading ->

        }

        viewModel.errors.observe(viewLifecycleOwner) { error ->

        }

        viewModel.listData.observe(viewLifecycleOwner) { list ->

        }
    }
}