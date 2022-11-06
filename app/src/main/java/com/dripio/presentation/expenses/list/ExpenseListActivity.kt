package com.dripio.presentation.expenses.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dripio.domain.entity.Expense
import com.dripio.presentation.base.PAYMENT_METHOD_ID
import com.dripio.presentation.base.startExpenseEditor
import com.dripio.presentation.paymentMethods.list.PaymentMethodListActivity
import com.example.dripio.R
import com.example.dripio.databinding.ActivityExpenseListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

const val SELECT_EXPENSE = "SELECT_EXPENSE"

class ExpenseListActivity : AppCompatActivity(), ExpenseListAdapter.Callback {
    private lateinit var binding: ActivityExpenseListBinding
    private lateinit var adapter: ExpenseListAdapter
    private val viewModel: ExpenseListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        initViews()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchExpenses()
    }

    private fun initObservers() {
        viewModel.expenses.observe(this) {
            adapter.setItems(it)
        }
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        initAdapter()
        binding.rvExpenses.adapter = adapter
        binding.rvExpenses.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initAdapter() {
        adapter = ExpenseListAdapter(this, this)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_payment_list_options, menu)
        menu.findItem(R.id.menu_payment_method_editor)?.let {
            it.setOnMenuItemClickListener {
                startExpenseEditor()
                true
            }
        }
        return true
    }

    override fun onSelectExpense(expense: Expense) {
        val intent = Intent()
        intent.putExtra(SELECT_EXPENSE, expense.id)
        setResult(RESULT_OK, intent)
        finish()
    }

    class ExpenseListActivityContract : ActivityResultContract<Unit, Long?>() {
        override fun createIntent(context: Context, input: Unit): Intent =
            Intent(context, ExpenseListActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?): Long? {
            return intent?.extras?.getLong(SELECT_EXPENSE)
        }
    }
}