package com.dripio.presentation.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Domain, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private var data = listOf<Domain>()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    val items: List<Domain> get() { return data }

    fun setItems(items: List<Domain>) {
        data = items
    }

    override fun getItemCount(): Int = data.size

    abstract class BaseViewHolder<Domain>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: Domain)
    }
}
