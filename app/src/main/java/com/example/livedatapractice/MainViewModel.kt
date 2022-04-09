package com.example.livedatapractice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {

    val questionCount = QuestionRepository.questionList.size-1

    val questionNumber = MutableLiveData<Int>(0)

    val hintText = Transformations.map(questionNumber) {
        if (it < questionCount / 2)
            "faster"
        else
            "you are dying"

    }


    val questionText = MutableLiveData<String>(
        QuestionRepository.questionList[0].question
    )
    val answerText = MutableLiveData<Int>(
        QuestionRepository.questionList[0].answer
    )
    val answerList = MutableLiveData<ArrayList<Int>>(
        arrayListOf(QuestionRepository.questionList[0].answer,
        fakeAnswer()[0],fakeAnswer()[1],fakeAnswer()[2])
    )


    var nextEnabledLiveData = MutableLiveData<Boolean>(true)
    var backEnabledLiveData = MutableLiveData<Boolean>(false)

    fun nextClicked() {
        if (questionNumber.value!! == questionCount-1) {
            questionNumber.value = questionNumber.value?.plus(1)
            nextEnabledLiveData.value = false
        }else{
            backEnabledLiveData.value = true
            questionNumber.value = questionNumber.value?.plus(1)
        }
        questionNumber.value?.let{ number ->
            questionText.value = QuestionRepository.questionList[number].question
            answerText.value = QuestionRepository.questionList[number].answer
        }
        allAnswers()

    }
    fun backClicked() {
        if (questionNumber.value!! == 1) {
            questionNumber.value = questionNumber.value?.minus(1)
            backEnabledLiveData.value = false
        }else{
            nextEnabledLiveData.value = true
            questionNumber.value = questionNumber.value?.minus(1)
        }
        questionNumber.value?.let{ number ->
            questionText.value = QuestionRepository.questionList[number].question
            answerText.value = QuestionRepository.questionList[number].answer
        }
        allAnswers()
    }
    private fun fakeAnswer(): ArrayList<Int> {
        var fakes = arrayListOf<Int>()
        var fake =QuestionRepository.questionList[questionNumber.value!!].answer
        for (i in 0..2) {
            while (fake == QuestionRepository.questionList[questionNumber.value!!].answer
                || fakes.contains(fake)) {
                fake = ((0..180).random())
            }
            fakes.add(fake)
        }
        return fakes
    }

    fun allAnswers() {
        var answers = fakeAnswer()
        answerText.value?.let { answers.add(it) }
        answers.shuffle()
        answerList.value = answers
    }

}