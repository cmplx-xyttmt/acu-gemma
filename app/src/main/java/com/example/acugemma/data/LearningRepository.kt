package com.example.acugemma.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Map
import com.example.acugemma.R

object LearningRepository {

    fun getSubjects(): List<Subject> = listOf(
        Subject(
            id = "math",
            name = "Mathematics",
            description = "Explore numbers, shapes, and problem-solving",
            imageDrawableInt = R.drawable.math_image,
            topics = getMathTopics()
        ),
        Subject(
            id = "geography",
            name = "Geography",
            description = "Discover the world, its landscapes, and cultures.",
            imageDrawableInt = R.drawable.geography_image,
            topics = getGeographyTopics()
        )
    )

    private fun getMathTopics(): List<Topic> = listOf(
        Topic("addition and subtraction", "Addition", "Numbers and Operations", Icons.Default.Add),
        Topic("multiplication and division", "Multiplication", "Numbers and Operations", Icons.Default.Close),
        Topic("shapes", "Shapes", "Geometry", Icons.Default.CropSquare),
        Topic("money", "Money", "Numbers and Operations", Icons.Default.AttachMoney),
        Topic("time", "Time", "Measurement", Icons.Default.Schedule)
    )

    private fun getGeographyTopics(): List<Topic> = listOf(
        Topic("countries", "Countries", "World Geography", Icons.Default.Public),
        Topic("continents", "Continents", "World Geography", Icons.Default.Language),
        Topic("oceans", "Oceans", "World Geography", Icons.Default.Waves),
        Topic("weather", "Weather", "World Geography", Icons.Default.Cloud),
        Topic("maps", "Maps", "World Geography", Icons.Default.Map),
        Topic("capitals", "Capitals", "World Geography", Icons.Default.LocationCity)
    )

    fun getSubjectByName(name: String): Subject? =
        getSubjects().find { it.name.equals(name, ignoreCase = true) }
}
