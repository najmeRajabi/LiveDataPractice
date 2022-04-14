package com.example.livedatapractice

import android.content.Context
import androidx.lifecycle.LiveData

object QuestionRepository {

    var db : AppDatabase? = null
    var questionDao  : QuestionDao? = null

    fun initDB(context : Context){
        db = AppDatabase.getAppDataBase(context)

        questionDao = db?.questionDao()


    }


    val questionList = arrayListOf(
       Question( 1," result of 2 + 2 ",4) ,
        Question(2," result of  5 - 1 " ,4),
        Question(3," result of 100 * 21",2100),
        Question(4," result of 100 - 21",79),
        Question(5," result of 100 + 21",121),
        Question(6," result of 5 + 6",11),
        Question(7," result of 10 * 21",210),
    )

    fun newRandomQuestion():Question{
        val random1= (0..100).random()
        val random2= (0..100).random()
        return Question(1,"result of $random1 - $random2",random1-random2 )

    }

    fun insertQuestionRandom(){
        db?.questionDao()?.insertAll(newRandomQuestion())
    }

    fun countAllQuestions(): LiveData<Int>? {
        return db?.questionDao()?.countAll()
    }

    fun getQuestion(number:Int): Question? {
        return db?.questionDao()?.getQuestion(number)
    }
}

