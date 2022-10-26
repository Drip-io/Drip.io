package com.example.dripio.presentation.payments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dripio.databinding.ViewPaymentItemBinding
import com.example.dripio.domain.entity.Payment
import com.example.dripio.extensions.toMoneyStringWithComma

class PaymentListAdapter(private val context: Context, val callback: Callback) :
    RecyclerView.Adapter<PaymentListAdapter.PaymentViewHolder>() {

    var payments: List<Payment> = listOf()
        set(value) {
            notifyDataSetChanged()
            field = value
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = ViewPaymentItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return PaymentViewHolder(view.root, callback)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        payments.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = payments.size

    class PaymentViewHolder(private val view: View, private val onClickItem: Callback) : RecyclerView.ViewHolder(view) {
        fun bind(payment: Payment) {
            val view = ViewPaymentItemBinding.bind(view)
            view.tvPaymentName.text = payment.name
            view.tvPaymentValue.text = payment.paymentValue.toMoneyStringWithComma()
            view.root.setOnClickListener {
                onClickItem.onClickListener(payment)
            }
        }
    }

    interface Callback {
        fun onClickListener(payment: Payment)
    }
}