package com.dripio.presentation.expenses.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dripio.domain.entity.Expense
import com.dripio.extensions.toMoneyStringWithComma
import com.example.dripio.R
import com.example.dripio.databinding.ViewExpenseItemBinding

class ExpenseListAdapter(private val context: Context, private val callback: Callback? = null) :
    Adapter<ExpenseListAdapter.ExpenseViewHolder>() {

    private var data = listOf<Expense>()
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    fun setItems(expenses: List<Expense>) {
        this.data = expenses
    }

    inner class ExpenseViewHolder(private val view: View) : ViewHolder(view) {
        fun bind(expense: Expense) {
            val item = ViewExpenseItemBinding.bind(view)
            item.tvName.text = expense.name
            item.tvExpenseValue.text = expense.totalValue.toMoneyStringWithComma()
            item.tvInstallments.text = context.getString(
                R.string.installment_indicator,
                expense.relatedPayments.size,
                expense.installments ?: 0
            )
            item.root.setOnClickListener {
                callback?.onSelectExpense(expense)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = ViewExpenseItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ExpenseViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        data.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    interface Callback {
        fun onSelectExpense(expense: Expense)
    }
}