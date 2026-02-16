package com.example.quizapp

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

        // FIX: Handle Window Insets to prevent Edge-to-Edge crash
        val rootView = findViewById<View>(R.id.main)
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        // Initialize Views
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.Question_text_view)
        flagImage = findViewById(R.id.image_view)
        checkButton = findViewById(R.id.btn_check)
        textViewOptionOne = findViewById(R.id.tv_option_one)
        textViewOptionTwo = findViewById(R.id.tv_option_two)
        textViewOptionThree = findViewById(R.id.tv_option_three)
        textViewOptionFour = findViewById(R.id.tv_option_four)

        // Set Click Listeners
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

        if (mCurrentPosition == mQuestionList.size) {
            checkButton.text = "FINISH"
        } else {
            checkButton.text = "SUBMIT"
        }

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
        val options = ArrayList<TextView>()
        options.add(textViewOptionOne)
        options.add(textViewOptionTwo)
        options.add(textViewOptionThree)
        options.add(textViewOptionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
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
                        finish()
                    }
                } else {
                    val question = mQuestionList[mCurrentPosition - 1]
                    if (question.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionList.size) {
                        checkButton.text = "FINISH"
                    } else {
                        checkButton.text = "GO TO NEXT QUESTION"
                    }
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
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> textViewOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            2 -> textViewOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            3 -> textViewOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            4 -> textViewOptionFour.background = ContextCompat.getDrawable(this, drawableView)
        }
    }
}