package com.example.livedatapractice

import android.content.Context
import androidx.lifecycle.LiveData

object QuestionRepository {

    var db : AppDatabase? = null
    var questionDao  : QuestionDao? = null



    fun initDB(context : Context){
        db = AppDatabase.getAppDataBase(context)

        questionDao = db?.questionDao()


        addTestData()
    }

    private fun addTestData() {
        questionDao?.insertAll(
            Question( 1," result of 2 + 2 ",4) ,
            Question(2," result of  5 - 1 " ,4),
            Question(3," result of 100 * 21",2100),
            Question(4," result of 100 - 21",79),
            Question(5," result of 100 + 21",121)
        )
    }




    fun newRandomQuestion():Question{
        val random1= (0..100).random()
        val random2= (0..100).random()
        return Question(0,question = "result of $random1 - $random2",answer=random1-random2 )

    }

    fun insertQuestionRandom(){
        questionDao?.insertAll(newRandomQuestion())
    }

    fun countAllQuestions(): LiveData<Int>? {
        return questionDao?.countAll()
    }

    fun getQuestion(number:Int): Question? {
        return db?.questionDao()?.getQuestion(number)
    }
    fun getQuestions(): List<Question>? {
        return db?.questionDao()?.getAll()
    }
    fun getQuestionLiveData(number: Int): LiveData<Question>? {
        return db?.questionDao()?.getQuestionLiveData(number)
    }
}

