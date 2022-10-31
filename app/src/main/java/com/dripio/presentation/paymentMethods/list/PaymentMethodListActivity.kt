package com.dripio.presentation.paymentMethods.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dripio.domain.entity.PaymentMethod
import com.dripio.extensions.visibleOrGone
import com.dripio.presentation.base.PAYMENT_METHOD_ID
import com.dripio.presentation.base.showConfirmDeletePaymentMethod
import com.dripio.presentation.base.startPaymentMethodEditor
import com.dripio.presentation.paymentMethods.list.adapter.PaymentMethodListAdapter
import com.dripio.presentation.paymentMethods.list.vm.PaymentMethodListViewModel
import com.example.dripio.R
import com.example.dripio.databinding.ActivityPaymentMethodListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentMethodListActivity : AppCompatActivity(), PaymentMethodListAdapter.Callback {
    private lateinit var binding: ActivityPaymentMethodListBinding
    private lateinit var adapter: PaymentMethodListAdapter
    private val viewModel: PaymentMethodListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewModel.paymentMethods.observe(this) {
            if (it.isNotEmpty()) {
                adapter.setItems(it)
            }

            binding.boxNoPaymentMethods.visibleOrGone(it.isEmpty())
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchPaymentMethods()
    }

    private fun initViews() {
        setupActionBar()
        initAdapter()
        binding.rvPaymentMethods.adapter = adapter
        binding.rvPaymentMethods.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_payment_list_options, menu)
        menu.findItem(R.id.menu_payment_method_editor)?.let {
            it.setOnMenuItemClickListener {
                startPaymentMethodEditor()
                true
            }
        }
        return true
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initAdapter() {
        adapter = PaymentMethodListAdapter(this, this)
    }

    override fun clickItem(paymentMethod: PaymentMethod) {
        finishWithResult(paymentMethod.id)
    }

    override fun clickEditItem(paymentMethod: PaymentMethod) {
    }

    override fun clickDeleteItem(paymentMethod: PaymentMethod) {
        showConfirmDelete(paymentMethod)
    }

    private fun finishWithResult(paymentMethodId: Long) {
        intent.putExtra(PAYMENT_METHOD_ID, paymentMethodId)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun showConfirmDelete(paymentMethod: PaymentMethod) {
        showConfirmDeletePaymentMethod(paymentMethod) {
            viewModel.deletePaymentMethod(paymentMethod.id) {
                viewModel.fetchPaymentMethods()
            }
        }
    }

    class PaymentSelectorActivityContract : ActivityResultContract<Unit, Long?>() {
        override fun createIntent(context: Context, input: Unit): Intent =
            Intent(context, PaymentMethodListActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?): Long? {
            return intent?.extras?.getLong(PAYMENT_METHOD_ID)
        }
    }
}
