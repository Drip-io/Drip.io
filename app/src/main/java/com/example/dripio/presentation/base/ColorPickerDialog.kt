package com.example.dripio.presentation.base

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import com.example.dripio.databinding.ViewColorPickerBinding

class ColorPickerDialog {
    companion object {
        fun rgbToHex(r: Int, g: Int, b: Int) = String.format("#%02x%02x%02x", r, g, b)

        fun show(context: Context, startColor: Int = 0, onConfirm: (Int, Int, Int) -> Unit) {
            var r = Color.red(startColor)
            var g = Color.green(startColor)
            var b = Color.blue(startColor)

            val buildingDialog = AlertDialog.Builder(context)
            buildingDialog.setPositiveButton(
                "Confirmar"
            ) { _, _ ->
                onConfirm.invoke(r, g, b)
            }
            val builtDialog = buildingDialog.create()
            val view = ViewColorPickerBinding.inflate(LayoutInflater.from(context))
            view.sliderRed.value = r.toFloat()
            view.sliderGreen.value = g.toFloat()
            view.sliderBlue.value = b.toFloat()
            view.boxColor.setBackgroundColor(Color.rgb(r, g, b))

            view.sliderRed.addOnChangeListener { _, value, _ ->
                r = value.toInt()
                view.boxColor.setBackgroundColor(Color.rgb(r, g, b))
                val hexColor = rgbToHex(r, g, b)
                view.tvColor.text = hexColor
            }
            view.sliderGreen.addOnChangeListener { _, value, _ ->
                g = value.toInt()
                view.boxColor.setBackgroundColor(Color.rgb(r, g, b))
                val hexColor = rgbToHex(r, g, b)
                view.tvColor.text = hexColor
            }
            view.sliderBlue.addOnChangeListener { _, value, _ ->
                b = value.toInt()
                view.boxColor.setBackgroundColor(Color.rgb(r, g, b))
                val hexColor = rgbToHex(r, g, b)
                view.tvColor.text = hexColor
            }
            builtDialog.setView(view.root)
            builtDialog.show()
        }
    }
}
