package com.dripio.presentation.paymentMethods.editor

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dripio.presentation.base.ColorPickerDialog
import com.dripio.presentation.paymentMethods.editor.vm.PaymentMethodEditorViewModel
import com.example.dripio.databinding.ActivityPaymentMethodEditorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentMethodEditorActivity : AppCompatActivity() {

    private val viewModel: PaymentMethodEditorViewModel by viewModel()
    private lateinit var binding: ActivityPaymentMethodEditorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
    }

    private fun initViews() {
        setSupportActionBar(binding.toolbar)

        binding.colorCard.root.setOnClickListener {
            val initialColor = try {
                Color.parseColor(viewModel.selectedColor.value)
            } catch (ex: Exception) {
                0
            }
            ColorPickerDialog.show(this, initialColor) { r, g, b ->
                val hexColor = String.format("#%02x%02x%02x", r, g, b)
                viewModel.setSelectedColor(hexColor)
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
            finish()
        }
    }

    private fun initObservers() {
        viewModel.selectedColor.observe(this) {
            setColor(it)
        }
    }

    private fun setColor(color: String) {
        binding.colorCard.tvColor.text = color
        binding.colorCard.ivColor.setColorFilter(Color.parseColor(color))
    }
}
