package com.example.lr3.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {
    fun formatIsoToRussian(iso: String): String = try {
        LocalDate.parse(iso)
            .format(DateTimeFormatter.ofPattern("d MMMM yyyy года", Locale("ru")))
    } catch (e: Exception) {
        iso
    }
}
