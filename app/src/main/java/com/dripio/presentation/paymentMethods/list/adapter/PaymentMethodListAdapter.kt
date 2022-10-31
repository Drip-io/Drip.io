package com.dripio.presentation.paymentMethods.list.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dripio.domain.entity.PaymentMethod
import com.dripio.presentation.base.BaseAdapter
import com.example.dripio.databinding.ViewPaymentMethodItemBinding

class PaymentMethodListAdapter(
    private val context: Context,
    private val callback: Callback? = null
) : BaseAdapter<PaymentMethod, PaymentMethodListAdapter.AdapterViewHolder>() {

    class AdapterViewHolder(private val view: View, private val onClick: (PaymentMethod) -> Unit, private val onClickDelete: (PaymentMethod) -> Unit) :
        BaseViewHolder<PaymentMethod>(view) {
        override fun bind(item: PaymentMethod) {
            val itemView = ViewPaymentMethodItemBinding.bind(view)
            itemView.tvName.text = item.name
            itemView.root.setOnClickListener {
                onClick.invoke(item)
            }
            itemView.ivColor.setColorFilter(Color.parseColor(item.color))
            itemView.ibDelete.setOnClickListener { onClickDelete.invoke(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view = ViewPaymentMethodItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return AdapterViewHolder(view.root, onClick = {
            callback?.clickItem(it)
        }, onClickDelete = {
                callback?.clickDeleteItem(it)
            })
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        items.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    interface Callback {
        fun clickItem(paymentMethod: PaymentMethod)
        fun clickEditItem(paymentMethod: PaymentMethod)
        fun clickDeleteItem(paymentMethod: PaymentMethod)
    }
}
