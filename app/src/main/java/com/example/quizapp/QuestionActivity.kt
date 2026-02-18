package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizapp.model.Question
import com.example.quizapp.utils.Constants

class QuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mSelectedOptionPosition: Int = 0
    private var mScore: Int = 0
    private lateinit var mQuestionList: MutableList<Question>

    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgress: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var flagImage: ImageView
    private lateinit var textViewOptionOne: TextView
    private lateinit var textViewOptionTwo: TextView
    private lateinit var textViewOptionThree: TextView
    private lateinit var textViewOptionFour: TextView
    private lateinit var checkButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_question)

        val rootView = findViewById<View>(R.id.main)
        rootView?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.Question_text_view)
        flagImage = findViewById(R.id.image_view)
        checkButton = findViewById(R.id.btn_check)
        textViewOptionOne = findViewById(R.id.tv_option_one)
        textViewOptionTwo = findViewById(R.id.tv_option_two)
        textViewOptionThree = findViewById(R.id.tv_option_three)
        textViewOptionFour = findViewById(R.id.tv_option_four)

        textViewOptionOne.setOnClickListener(this)
        textViewOptionTwo.setOnClickListener(this)
        textViewOptionThree.setOnClickListener(this)
        textViewOptionFour.setOnClickListener(this)
        checkButton.setOnClickListener(this)

        mQuestionList = Constants.getQuestions()
        progressBar.max = mQuestionList.size

        setQuestion()
    }

    private fun setQuestion() {
        val question = mQuestionList[mCurrentPosition - 1]
        defaultOptionsView()

        checkButton.text =
            if (mCurrentPosition == mQuestionList.size) "FINISH"
            else "SUBMIT"

        progressBar.progress = mCurrentPosition
        tvProgress.text = "$mCurrentPosition/${mQuestionList.size}"
        tvQuestion.text = question.question
        flagImage.setImageResource(question.image)
        textViewOptionOne.text = question.optionOne
        textViewOptionTwo.text = question.optionTwo
        textViewOptionThree.text = question.optionThree
        textViewOptionFour.text = question.optionFour
    }

    private fun defaultOptionsView() {
        val options = arrayListOf(
            textViewOptionOne,
            textViewOptionTwo,
            textViewOptionThree,
            textViewOptionFour
        )

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.tv_option_one -> selectedOptionView(textViewOptionOne, 1)
            R.id.tv_option_two -> selectedOptionView(textViewOptionTwo, 2)
            R.id.tv_option_three -> selectedOptionView(textViewOptionThree, 3)
            R.id.tv_option_four -> selectedOptionView(textViewOptionFour, 4)

            R.id.btn_check -> {

                if (mSelectedOptionPosition == 0) {

                    mCurrentPosition++

                    if (mCurrentPosition <= mQuestionList.size) {
                        setQuestion()
                    } else {

                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra("score", mScore)
                        intent.putExtra("total", mQuestionList.size)
                        startActivity(intent)
                        finish()
                    }

                } else {

                    val question = mQuestionList[mCurrentPosition - 1]

                    if (question.correctAnswer == mSelectedOptionPosition) {
                        mScore++
                    } else {
                        answerView(
                            mSelectedOptionPosition,
                            R.drawable.wrong_option_border_bg
                        )
                    }

                    answerView(
                        question.correctAnswer,
                        R.drawable.correct_option_border_bg
                    )

                    checkButton.text =
                        if (mCurrentPosition == mQuestionList.size)
                            "FINISH"
                        else
                            "GO TO NEXT QUESTION"

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background =
            ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> textViewOptionOne.background =
                ContextCompat.getDrawable(this, drawableView)

            2 -> textViewOptionTwo.background =
                ContextCompat.getDrawable(this, drawableView)

            3 -> textViewOptionThree.background =
                ContextCompat.getDrawable(this, drawableView)

            4 -> textViewOptionFour.background =
                ContextCompat.getDrawable(this, drawableView)
        }
    }
}
