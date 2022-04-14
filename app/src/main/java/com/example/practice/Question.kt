package com.example.practice

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question (
    @PrimaryKey(autoGenerate = true) val number: Int,
    val question :String,
    val answer: Int,
    var answered : Boolean=false,
    var correct: Boolean= false){
}