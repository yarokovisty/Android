package com.example.clock

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.clock.databinding.ActivityMain2Binding
import com.google.android.material.button.MaterialButton
import java.util.concurrent.TimeUnit
import kotlin.math.min

class MainActivity2 : AppCompatActivity() {
    private lateinit var bindingClass : ActivityMain2Binding
    private var pauseTimer = true
    private var timer: CountDownTimer? = null
    private var hours = 0
    private var minutes = 0
    private var seconds = 0
    private lateinit var dialog: Dialog
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.startBtn.setOnClickListener{
                pauseTimer = !pauseTimer

                if (!pauseTimer &&
                    (hours != 0 ||
                            minutes != 0 ||
                            seconds != 0)) { // start
                    Log.i("Start", "Start")
                    bindingClass.startBtn.text = getString(R.string.stop)
                    bindingClass.startBtn.setBackgroundColor(getColor(R.color.stop))

                    val duration = (hours * 3600 + minutes * 60 + seconds) * 1000L
                    Log.i("duration", duration.toString())

                    timer?.cancel()
                    timer = object : CountDownTimer(duration, 1000){
                        override fun onTick(millisUnilFinished: Long) {
                            hours = TimeUnit.MILLISECONDS.toHours(millisUnilFinished).toInt()
                            minutes = (TimeUnit.MILLISECONDS.toMinutes(millisUnilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUnilFinished))).toInt()
                            seconds = (TimeUnit.MILLISECONDS.toSeconds(millisUnilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUnilFinished))).toInt()

                            bindingClass.tvHour.text = String.format("%02d", hours)
                            bindingClass.tvMinute.text = String.format("%02d", minutes)
                            bindingClass.tvSecond.text = String.format("%02d", seconds)
                        }

                        override fun onFinish() {
                            val alarm = MediaPlayer.create(this@MainActivity2, R.raw.alarm)
                            alarm.start()
                            Toast.makeText(this@MainActivity2, "Time's up", Toast.LENGTH_SHORT).show()
                            pauseTimer = false
                            bindingClass.startBtn.text = getString(R.string.start)
                            bindingClass.startBtn.setBackgroundColor(getColor(R.color.start))

                        }
                    }.start()


                }
                else {
                    bindingClass.startBtn.text = getString(R.string.start)
                    bindingClass.startBtn.setBackgroundColor(getColor(R.color.start))
                    timer?.cancel()

                }
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
            if (e2.x > e1.x) {
                // Transition to ActivityB
                val intent = Intent(this@MainActivity2, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
            return true
        }
    }

    fun onClickHour(view: View) {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog_box)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_bg))
        dialog.setCancelable(false)

        val tvTime = dialog.findViewById<TextView>(R.id.tvTime)
        val btnOk = dialog.findViewById<MaterialButton>(R.id.btnOk)
        val btnCancel = dialog.findViewById<MaterialButton>(R.id.btnCancel)
        val etTime = dialog.findViewById<EditText>(R.id.edTime)

        btnOk.setOnClickListener{
            hours = etTime.text.toString().toInt()
            bindingClass.tvHour.text = String.format("%02d", hours)
            dialog.dismiss()
        }

        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        tvTime.text = getString(R.string.hour)
        dialog.show()

    }

    fun onClickMinute(view: View) {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog_box)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_bg))
        dialog.setCancelable(false)

        val tvTime = dialog.findViewById<TextView>(R.id.tvTime)
        val btnOk = dialog.findViewById<MaterialButton>(R.id.btnOk)
        val btnCancel = dialog.findViewById<MaterialButton>(R.id.btnCancel)
        val etTime = dialog.findViewById<EditText>(R.id.edTime)

        btnOk.setOnClickListener{
            minutes = etTime.text.toString().toInt()
            bindingClass.tvMinute.text = String.format("%02d", minutes)
            dialog.dismiss()
        }

        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        tvTime.text = getString(R.string.minute)
        dialog.show()
    }

    fun onClickSecond(view: View) {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog_box)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_bg))
        dialog.setCancelable(false)

        val tvTime = dialog.findViewById<TextView>(R.id.tvTime)
        val btnOk = dialog.findViewById<MaterialButton>(R.id.btnOk)
        val btnCancel = dialog.findViewById<MaterialButton>(R.id.btnCancel)
        val etTime = dialog.findViewById<EditText>(R.id.edTime)

        btnOk.setOnClickListener{
            seconds = etTime.text.toString().toInt()
            bindingClass.tvSecond.text = String.format("%02d", seconds)
            dialog.dismiss()
        }

        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        tvTime.text = getString(R.string.second)
        dialog.show()
    }


    fun onClickStopWatch(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    fun onClickClear(view: View) {
        hours = 0
        minutes = 0
        seconds = 0
        bindingClass.tvHour.text = "00"
        bindingClass.tvMinute.text = "00"
        bindingClass.tvSecond.text = "00"
        pauseTimer = false
        bindingClass.startBtn.text = getString(R.string.start)
        bindingClass.startBtn.setBackgroundColor(getColor(R.color.start))
        timer?.cancel()
    }


}