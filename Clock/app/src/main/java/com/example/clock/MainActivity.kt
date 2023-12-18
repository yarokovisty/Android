package com.example.clock

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import com.example.clock.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass : ActivityMainBinding
    private lateinit var stopwatch : StopWatch
    private var intervalList = arrayListOf("Интервал")
    private lateinit var arrAdapter : ArrayAdapter<String>
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        stopwatch = StopWatch(bindingClass)
        arrAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, intervalList)
        bindingClass.listIntervals.adapter = arrAdapter

        bindingClass.btnInterval.setOnClickListener {
            if (!stopwatch.pause) {
                val time = String.format(
                    "%02d:%02d:%02d",
                    stopwatch.hours,
                    stopwatch.minutes,
                    stopwatch.seconds
                )
                intervalList.add(time)
                bindingClass.listIntervals.visibility = View.VISIBLE
                arrAdapter.notifyDataSetChanged()
            }
        }

        bindingClass.btnreset.setOnClickListener{
            stopwatch.resetStopWatch()
            bindingClass.btnStart.text = resources.getString(R.string.start)
            bindingClass.btnStart.icon = getDrawable(R.drawable.baseline_play_arrow_24)
            intervalList.clear()
            intervalList.add("Интервал")
            bindingClass.listIntervals.visibility = View.INVISIBLE
            arrAdapter.notifyDataSetChanged()
        }

        gestureDetector = GestureDetector(this, MyGestureListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            // Detect swipe to the right
            if (e2.x < e1.x) {
                // Transition to ActivityB
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
            return true
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickStart(view: View) {
        if (stopwatch.pause) {
            bindingClass.btnStart.text = resources.getString(R.string.stop)
            bindingClass.btnStart.icon = getDrawable(R.drawable.baseline_pause_24)
            stopwatch.startStopWatch()
        }
        else {
            bindingClass.btnStart.text = resources.getString(R.string.start)
            bindingClass.btnStart.icon = getDrawable(R.drawable.baseline_play_arrow_24)
            stopwatch.pauseStopWatch()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickReset(view: View) {
        bindingClass.btnreset.setOnClickListener{
            stopwatch.resetStopWatch()
            bindingClass.btnStart.text = resources.getString(R.string.start)
            bindingClass.btnStart.icon = getDrawable(R.drawable.baseline_play_arrow_24)
            intervalList.clear()
            intervalList.add("Интервал")
            arrAdapter.notifyDataSetChanged()
        }
    }

    fun onClickAddInterval(view: View) {
        bindingClass.btnInterval.setOnClickListener {
            if (!stopwatch.pause) {
                val time = String.format(
                    "%02d:%02d:%02d",
                    stopwatch.hours,
                    stopwatch.minutes,
                    stopwatch.seconds
                )
                intervalList.add(time)
                arrAdapter.notifyDataSetChanged()
            }
        }

    }

    fun onClickTimer(view: View) {
        val intent = Intent(this, MainActivity2::class.java)

        startActivity(intent)
        overridePendingTransition(0, 0)
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i("Destroy", "BOOM")
    }

}