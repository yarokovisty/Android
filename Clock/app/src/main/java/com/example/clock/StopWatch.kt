package com.example.clock

import android.os.Looper
import android.os.Handler
import com.example.clock.databinding.ActivityMainBinding




class StopWatch(private val bindingClass: ActivityMainBinding) {
    var hours = 0
    var seconds = 0
    var minutes = 0
    var pause = true
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            seconds++

            if (seconds == 60) {
                seconds = 0
                minutes++
            }
            if (minutes == 60) {
                minutes = 0
                hours++
            }
            updateStopWatchText()

            handler.postDelayed(this, 1000)
        }

    }


    fun startStopWatch() {
        pause = false
        handler.postDelayed(runnable, 0)
    }

    fun pauseStopWatch() {
        pause = true
        handler.removeCallbacks(runnable)
    }

    fun resetStopWatch() {
        pause = true
        hours = 0
        seconds = 0
        minutes = 0
        handler.removeCallbacks(runnable)
        updateStopWatchText()
        bindingClass.imgStopWatch.setImageResource(R.drawable.c)
    }

    private fun updateStopWatchText() {
        val timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        bindingClass.timeStopWatch.text = timeText


        when(seconds / 5) {
            0 -> bindingClass.imgStopWatch.setImageResource(R.drawable.c0)
            1 -> bindingClass.imgStopWatch.setImageResource(R.drawable.c1)
            2 -> bindingClass.imgStopWatch.setImageResource(R.drawable.c2)
            3 -> bindingClass.imgStopWatch.setImageResource(R.drawable.c3)
            4 -> bindingClass.imgStopWatch.setImageResource(R.drawable.c4)
            5 -> bindingClass.imgStopWatch.setImageResource(R.drawable.c5)
            6 -> bindingClass.imgStopWatch.setImageResource(R.drawable.c6)
            7 -> bindingClass.imgStopWatch.setImageResource(R.drawable.c7)
            8 -> bindingClass.imgStopWatch.setImageResource(R.drawable.c8)
            9 -> bindingClass.imgStopWatch.setImageResource(R.drawable.c9)
            10 -> bindingClass.imgStopWatch.setImageResource(R.drawable.c10)
            11 -> bindingClass.imgStopWatch.setImageResource(R.drawable.c11)
        }


    }
}