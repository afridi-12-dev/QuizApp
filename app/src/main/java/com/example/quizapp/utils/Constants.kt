package com.example.quizapp.utils

import com.example.quizapp.R
import com.example.quizapp.model.Question

object Constants {

    fun getQuestions(): MutableList<Question> {

        val questionList = mutableListOf<Question>()

        // Question 1 - Brazil
        val question1 = Question(
            1,
            "What country does this flag belong to?",
            R.drawable.brazil,
            "Argentina",
            "India",
            "Spain",
            "Brazil",
            4
        )

        // Question 2 - Italy
        val question2 = Question(
            2,
            "What country does this flag belong to?",
            R.drawable.italy,
            "France",
            "Italy",
            "Germany",
            "Ireland",
            2
        )

        // Question 3 - Spain
        val question3 = Question(
            3,
            "What country does this flag belong to?",
            R.drawable.spain,
            "Portugal",
            "Mexico",
            "Spain",
            "Argentina",
            3
        )

        // Question 4 - Germany
        val question4 = Question(
            4,
            "What country does this flag belong to?",
            R.drawable.germany,
            "Belgium",
            "Austria",
            "Germany",
            "Netherlands",
            3
        )

        // Question 5 - Argentina
        val question5 = Question(
            5,
            "What country does this flag belong to?",
            R.drawable.argantina,
            "Uruguay",
            "Brazil",
            "Chile",
            "Argentina",
            4
        )

        questionList.add(question1)
        questionList.add(question2)
        questionList.add(question3)
        questionList.add(question4)
        questionList.add(question5)

        return questionList
    }
}
