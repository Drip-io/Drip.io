package com.dripio.presentation.base.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.dripio.extensions.closeKeyboard
import com.dripio.extensions.setText
import com.dripio.extensions.toMoneyStringWithPeriod
import com.example.dripio.databinding.ComponentCurrentTextInputBinding
import com.google.android.material.textfield.TextInputLayout

class TextInputCurrency @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : TextInputLayout(context, attributeSet, defStyle) {
    private var binding: ComponentCurrentTextInputBinding

    var onUpdateValue: ((Float) -> Unit)? = null

    val value: Float
        get() {
            return binding.tiValue.editText?.text?.toString()?.toFloatOrNull() ?: 0F
        }

    private fun setValueFieldText(text: String) {
        binding.tiValue.editText?.editableText?.setText(text)
    }

    private fun updateValueToInput(value: Float) {
        onUpdateValue?.invoke(value)
        setValueFieldText(value.toMoneyStringWithPeriod())
    }

    private fun updateValueToZero() {
        onUpdateValue?.invoke(0F)
        setValueFieldText("0.00")
    }

    init {
        binding = ComponentCurrentTextInputBinding.inflate(LayoutInflater.from(context), this)

        binding.tiValue.editText?.setOnFocusChangeListener { _, _ ->
            binding.tiValue.editText?.text?.toString()?.toFloatOrNull()?.let {
                updateValueToInput(it)
            } ?: run {
                updateValueToZero()
            }
        }

        binding.tiValue.editText?.let {
            it.setOnEditorActionListener { _, _, _ ->
                it.clearFocus()
                it.closeKeyboard()
                return@setOnEditorActionListener true
            }
        }
    }
}