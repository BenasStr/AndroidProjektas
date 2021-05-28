package edu.ktu.ride.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Trick(
    @PrimaryKey(autoGenerate = true) val trickId: Int = 0,
    val name: String = "",
    val requirements: String = "",
    val tutorial_vid: String = "",
    val description: String = "",
    val learning_status: Int = 0,
    val categorie: String = "",
    val position: String = "",
    val done: Int = 0,
)