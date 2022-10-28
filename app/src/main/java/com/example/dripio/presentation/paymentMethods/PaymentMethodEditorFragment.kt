package com.example.dripio.presentation.paymentMethods

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dripio.R.layout.fragment_payment_method_editor
import com.example.dripio.databinding.FragmentPaymentMethodEditorBinding
import com.example.dripio.presentation.base.ColorPickerDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PaymentMethodEditorFragment : Fragment() {

    private val viewModel: PaymentMethodsViewModel by sharedViewModel()
    private lateinit var binding: FragmentPaymentMethodEditorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentMethodEditorBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.colorCard.root.setOnClickListener {
            val initialColor = try {
                Color.parseColor(viewModel.selectedColor.value)
            } catch (ex: Exception) {
                0
            }
            context?.let { context ->
                ColorPickerDialog.show(context, initialColor) { r, g, b ->
                    val hexColor = String.format("#%02x%02x%02x", r, g, b)
                    viewModel.setSelectedColor(hexColor)
                }
            }
        }

        binding.bSave.setOnClickListener {
            val name = binding.tiName.editText?.editableText?.toString()
            val color = viewModel.selectedColor.value

            if (name == null) {
                return@setOnClickListener
            }

            if (color == null) {
                return@setOnClickListener
            }

            viewModel.addPaymentMethod(name, color)
        }
    }

    private fun initObservers() {
        viewModel.selectedColor.observe(viewLifecycleOwner) {
            setColor(it)
        }
    }

    private fun setColor(color: String) {
        binding.colorCard.tvColor.text = color
        binding.colorCard.ivColor.setColorFilter(Color.parseColor(color))
    }
}
