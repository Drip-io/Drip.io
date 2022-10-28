package com.example.dripio.presentation.paymentMethods

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dripio.databinding.ViewPaymentMethodItemBinding
import com.example.dripio.domain.entity.PaymentMethod
import com.example.dripio.presentation.base.BaseAdapter

class PaymentMethodListAdapter(private val context: Context) : BaseAdapter<PaymentMethod, PaymentMethodListAdapter.AdapterViewHolder>() {

    class AdapterViewHolder(private val view: View) : BaseViewHolder<PaymentMethod>(view) {
        override fun bind(item: PaymentMethod) {
            val itemView = ViewPaymentMethodItemBinding.bind(view)
            itemView.tvName.text = item.name
            itemView.ivColor.setColorFilter(Color.parseColor(item.color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view = ViewPaymentMethodItemBinding.inflate(LayoutInflater.from(context))
        return AdapterViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        items.getOrNull(position)?.let {
            holder.bind(it)
        }
    }
}
