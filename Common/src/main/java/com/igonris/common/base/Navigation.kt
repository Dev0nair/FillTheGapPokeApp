package com.igonris.common.base

import android.view.View

data class Navigation(
    val dest: Int,
    val args: List<Pair<String, Any>> = emptyList(),
    val sharedElements: List<Pair<View, String>>? = null
)