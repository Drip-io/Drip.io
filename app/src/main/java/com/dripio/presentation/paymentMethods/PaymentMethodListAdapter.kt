package com.dripio.presentation.paymentMethods

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dripio.domain.entity.PaymentMethod
import com.dripio.presentation.base.BaseAdapter
import com.example.dripio.databinding.ViewPaymentMethodItemBinding

class PaymentMethodListAdapter(private val context: Context, val callback: Callback? = null) : BaseAdapter<PaymentMethod, PaymentMethodListAdapter.AdapterViewHolder>() {

    class AdapterViewHolder(private val view: View, val onClick: (PaymentMethod) -> Unit) : BaseViewHolder<PaymentMethod>(view) {
        override fun bind(item: PaymentMethod) {
            val itemView = ViewPaymentMethodItemBinding.bind(view)
            itemView.tvName.text = item.name
            itemView.root.setOnClickListener {
                onClick.invoke(item)
            }
            itemView.ivColor.setColorFilter(Color.parseColor(item.color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view = ViewPaymentMethodItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return AdapterViewHolder(view.root) { callback?.clickItem(it) }
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        items.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    interface Callback {
        fun clickItem(paymentMethod: PaymentMethod)
    }
}
