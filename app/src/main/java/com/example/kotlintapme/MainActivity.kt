package com.example.kotlintapme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var score : Int = 0
    internal var gameStarted : Boolean = false
    internal lateinit var countDownTimer : CountDownTimer
    internal var initialCountDown : Long = 10000
    internal var countDownInterval : Long = 1000
    internal var timeLeftOnTimer : Long = 60000
    internal val TAG = "MainActivity"

    companion object {
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resetCountDown()
        btnTapMe.setOnClickListener {
            incrementScore()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer.cancel()
        Log.e(TAG, "Score is $score and time left $timeLeftOnTimer")
    }


    private fun resetCountDown(){
        score = 0
        gameStarted = false

        yourScore.text = getString(R.string.your_score, score.toString())
        val initalTimeLeft = initialCountDown / 1000
        tvTimeLeft.text = getString(R.string.time_left, initalTimeLeft.toString())

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval){
            override fun onFinish() {
                gameOver()
            }
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                timeLeftOnTimer = millisUntilFinished
                tvTimeLeft.text = getString(R.string.time_left, timeLeft.toString())
            }
        }
    }

    private fun gameOver(){
        Toast.makeText(this, getString(R.string.your_final_score, score.toString()), Toast.LENGTH_LONG).show()
        resetCountDown()
    }

    private fun incrementScore(){
        if(!gameStarted){
            countDownTimer.start()
            gameStarted = true
        }
        score ++
        val newScore = getString(R.string.your_score, score.toString())
        yourScore.text = newScore
    }
}
