package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val moveLeftToRight = AnimationUtils.loadAnimation(this, R.anim.move_left_right)
        bindingClass.btnPlayer1.startAnimation(moveLeftToRight)
        val moveRightToLeft = AnimationUtils.loadAnimation(this, R.anim.move_right_left)
        bindingClass.btnPlayer2.startAnimation(moveRightToLeft)
    }

    fun onClickPlayer1(view: View) {
        val intent = Intent(this, Player1::class.java)
        startActivity(intent)
    }

    fun onClickPlayer2(view: View) {
        val intent = Intent(this, Player2::class.java)
        startActivity(intent)

    }
}