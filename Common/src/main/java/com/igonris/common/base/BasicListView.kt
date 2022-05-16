package com.igonris.common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.igonris.common.R

class BasicListView(val items: List<String> = emptyList()) :
    RecyclerView.Adapter<BasicListView.BLH>() {

    inner class BLH(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BLH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_basic_list_view, parent, false)
        return BLH(view)
    }

    override fun onBindViewHolder(holder: BLH, position: Int) {
        val item = items[position]
        (holder.itemView as TextView).text = item
    }

    override fun getItemCount(): Int {
        return items.size
    }
}