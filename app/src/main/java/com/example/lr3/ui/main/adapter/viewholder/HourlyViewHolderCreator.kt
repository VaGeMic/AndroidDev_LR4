package com.example.lr3.ui.main.adapter.viewholder

import android.R
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lr3.databinding.ItemHourlyForecastBinding
import com.example.lr3.data.model.HourlyData
import androidx.core.graphics.ColorUtils

object HourlyViewHolderCreator {
    fun create(parent: ViewGroup, data: HourlyData): View {
        val binding = ItemHourlyForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        // выбираем цвета по температуре
        val temp = data.temperature
        val (startColor, endColor) = when {
            temp < -20 -> Color.WHITE to Color.BLUE
            temp < 0   -> Color.BLUE to Color.CYAN
            temp < 20  -> Color.CYAN to Color.YELLOW
            else       -> Color.YELLOW to Color.RED
        }
        var colorPar = temp
        while(colorPar+20<80) colorPar+=20
        colorPar-=60
        val percentOfStartColor = colorPar/20
        val percent = percentOfStartColor.coerceIn(0.0,1.0).toFloat()
        val blended = ColorUtils.blendARGB(startColor, endColor, percent)
        // создаём градиент
        //val gradient = GradientDrawable(
        //    GradientDrawable.Orientation.TOP_BOTTOM,
        //    intArrayOf(startColor, endColor)
        //).apply {
        //    cornerRadius = 8 * parent.context.resources.displayMetrics.density
        //}

        // накладываем фон на сам часовой Card
        binding.hourCard.setCardBackgroundColor(blended)

        // заполняем текст
        binding.textViewHour.text = data.time
        binding.textViewTemp.text = "${data.temperature}°C"
        return binding.root
    }
}