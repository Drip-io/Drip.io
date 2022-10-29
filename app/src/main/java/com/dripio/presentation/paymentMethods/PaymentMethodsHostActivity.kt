package com.dripio.presentation.paymentMethods

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dripio.presentation.base.PAYMENT_METHOD_ID
import com.example.dripio.R
import com.example.dripio.databinding.ActivityPaymentMethodsBinding

class PaymentMethodsHostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentMethodsBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        supportFragmentManager.findFragmentById(R.id.fragment_container)?.let {
            navController = it.findNavController()
        } ?: run {
            finish()
        }
    }

    fun finishWithResult(paymentMethodId: Long) {
        intent.putExtra(PAYMENT_METHOD_ID, paymentMethodId)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun initViews() {
        setupActionBar()
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
                navController.navigate(R.id.paymentMethodEditorFragment)
                true
            }
        }
        return true
    }
}
