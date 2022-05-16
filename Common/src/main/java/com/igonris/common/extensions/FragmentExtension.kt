package com.igonris.common.extensions

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController


fun Fragment.navigate(
    dest: Int,
    params: List<Pair<String, Any>>,
    sharedElements: List<Pair<View, String>>? = null
) {
    var extras: FragmentNavigator.Extras? = null

    if (sharedElements != null) {
        extras = FragmentNavigatorExtras(*sharedElements.toTypedArray())
    }

    findNavController().navigate(dest, bundleOf(*params.toTypedArray()), null, extras)
}

fun Fragment.showError(error: String) {
    context?.let { ctx ->
        Toast.makeText(ctx, error, Toast.LENGTH_SHORT).show()
    }
}