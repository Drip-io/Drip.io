package com.dripio.presentation.paymentMethods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dripio.databinding.FragmentPaymentMethodListBinding
import com.dripio.domain.entity.PaymentMethod
import com.dripio.extensions.visibleOrGone
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PaymentMethodListFragment : Fragment(), PaymentMethodListAdapter.Callback {
    private lateinit var binding: FragmentPaymentMethodListBinding
    private lateinit var adapter: PaymentMethodListAdapter
    private val viewModel: PaymentMethodsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentMethodListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        initViewModel()
    }

    private fun initObservers() {
        viewModel.paymentMethods.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setItems(it)
            }

            binding.boxNoPaymentMethods.visibleOrGone(it.isEmpty())
        }
    }

    private fun initViewModel() {
        viewModel.fetchPaymentMethods()
    }

    private fun initViews() {
        initAdapter()
        binding.rvPaymentMethods.adapter = adapter
        binding.rvPaymentMethods.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun initAdapter() {
        context?.let { context ->
            adapter = PaymentMethodListAdapter(context, this@PaymentMethodListFragment)
        }
    }

    override fun clickItem(paymentMethod: PaymentMethod) {
        (requireActivity() as? PaymentMethodsHostActivity)?.finishWithResult(paymentMethod.id)
    }
}
