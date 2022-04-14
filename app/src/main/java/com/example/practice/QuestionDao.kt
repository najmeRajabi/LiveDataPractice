package com.example.practice

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionDao {

    @Query("SELECT * FROM Question")
    fun getAll(): List<Question>

    @Query("SELECT * FROM Question WHERE number=:n")
    fun getQuestionLiveData(n : Int) : LiveData<Question>

    @Query("SELECT * FROM Question WHERE number = :n LIMIT 1")
    fun getQuestion( n : Int?) : Question?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg questions: Question)

    @Delete
    fun delete(question : Question)
}