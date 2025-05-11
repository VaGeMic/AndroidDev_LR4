package com.example.mycity.model

data class Recommendation(
    val categoryName: String,
    val name: String,
    val description: String,     // новое поле
    val imageResId: Int          // ресурс картинки из drawable
)
