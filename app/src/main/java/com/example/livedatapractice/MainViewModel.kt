package com.example.livedatapractice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {

    val questionCount = QuestionRepository.questionList.size-1

    val questionNumber = MutableLiveData<Int>(0)


    val questionText = MutableLiveData<String>(
        QuestionRepository.questionList[0]
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
            questionText.value = QuestionRepository.questionList[number]
        }
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
            questionText.value = QuestionRepository.questionList[number]
        }
    }

}