package com.osamu.existentialjournal.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.ui.graphics.vector.ImageVector

data class Mood(
    val id: String,
    val label: String,
    val icon: ImageVector
)

object MoodRepository {
    val moods = listOf(
        Mood("calm", "هادئ", Icons.Default.Eco),
        Mood("contemplative", "متأمل", Icons.Default.Visibility),
        Mood("solitary", "منعزل", Icons.Default.Face),
        Mood("lost_in_thought", "غارق في الأفكار", Icons.Default.Cloud)
    )

    fun getMoodById(id: String): Mood? = moods.find { it.id == id }
    
    fun getMoodByLabel(label: String): Mood? = moods.find { it.label == label }
}
