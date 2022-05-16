package com.igonris.common.base

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.igonris.common.extensions.navigate
import com.igonris.common.extensions.showError

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenCreated {
            viewModel.errors.observe(viewLifecycleOwner) { _error ->
                _error.doIfUnhandled { error ->
                    showError(error.desc)
                }
            }

            viewModel.navigation.observe(viewLifecycleOwner) { _navigation ->
                _navigation.doIfUnhandled { navigation ->
                    navigate(navigation.dest, navigation.args, navigation.sharedElements)
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

}