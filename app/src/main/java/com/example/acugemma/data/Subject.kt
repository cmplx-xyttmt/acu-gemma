package com.example.acugemma.data

data class Subject(
    val id: String,
    val name: String,
    val description: String,
    val imageDrawableInt: Int,
    val topics: List<Topic>
)
