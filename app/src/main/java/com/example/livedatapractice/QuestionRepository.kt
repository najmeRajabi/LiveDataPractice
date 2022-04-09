package com.example.livedatapractice

object QuestionRepository {
    val questionList = arrayListOf(
       Question( " result of 2 + 2 ",4) ,
        Question(" result of  5 - 1 " ,4),
        Question(" result of 100 * 21",2100),
        Question(" result of 100 - 21",79),
        Question(" result of 100 + 21",121),
        Question(" result of 5 + 6",11),
        Question(" result of 10 * 21",210),
    )
}

data class Question(
    val question :String ,
    val answer: Int ,
    var answered : Boolean=false,
    var correct: Boolean= false
)