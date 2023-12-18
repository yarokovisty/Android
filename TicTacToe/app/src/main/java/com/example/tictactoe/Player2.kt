package com.example.tictactoe

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.tictactoe.databinding.ActivityPlayer2Binding


class Player2 : AppCompatActivity() {
    private lateinit var bindingClass: ActivityPlayer2Binding
    private var game = TicTacToeP2()
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityPlayer2Binding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        val scale_up_field_game = AnimationUtils.loadAnimation(this, R.anim.increase_size)
        bindingClass.lGameField.startAnimation(scale_up_field_game)
    }

    fun onClickBack(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onClickRestart(view: View) {
        clearGameField()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField0(view: View) {
        if (bindingClass.imgField0.drawable == null) {
            if (game.movePlayer1)
            {
                bindingClass.imgField0.setImageResource(R.drawable.cross)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back_without_border)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back)
                game.arrFillFields[0] = "x"
                if (game.checkWinner()) outputWinner("x")
                else if(game.checkDraw()) outputWinner("d")
            }
            else {
                bindingClass.imgField0.setImageResource(R.drawable.zero)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back_without_border)
                game.arrFillFields[0] = "o"
                if (game.checkWinner()) outputWinner("o")
                else if(game.checkDraw()) outputWinner("d")
            }
            game.changeMove()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField1(view: View) {
        if (bindingClass.imgField1.drawable == null) {
            if (game.movePlayer1) {
                bindingClass.imgField1.setImageResource(R.drawable.cross)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back_without_border)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back)
                game.arrFillFields[1] = "x"
                if (game.checkWinner()) outputWinner("x")
                else if(game.checkDraw()) outputWinner("d")

            }
            else {
                bindingClass.imgField1.setImageResource(R.drawable.zero)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back_without_border)
                game.arrFillFields[1] = "o"
                if (game.checkWinner()) outputWinner("o")
                else if(game.checkDraw()) outputWinner("d")
            }
            game.changeMove()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField2(view: View) {
        if (bindingClass.imgField2.drawable == null) {
            if (game.movePlayer1) {
                bindingClass.imgField2.setImageResource(R.drawable.cross)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back_without_border)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back)
                game.arrFillFields[2] = "x"
                if (game.checkWinner()) outputWinner("x")
                else if(game.checkDraw()) outputWinner("d")
            }
            else {
                bindingClass.imgField2.setImageResource(R.drawable.zero)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back_without_border)
                game.arrFillFields[2] = "o"
                if (game.checkWinner()) outputWinner("o")
                else if(game.checkDraw()) outputWinner("d")
            }
            game.changeMove()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField3(view: View) {
        if (bindingClass.imgField3.drawable == null) {
            if (game.movePlayer1) {
                bindingClass.imgField3.setImageResource(R.drawable.cross)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back_without_border)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back)
                game.arrFillFields[3] = "x"
                if (game.checkWinner()) outputWinner("x")
                else if(game.checkDraw()) outputWinner("d")
            }
            else {
                bindingClass.imgField3.setImageResource(R.drawable.zero)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back_without_border)
                game.arrFillFields[3] = "o"
                if (game.checkWinner()) outputWinner("o")
                else if(game.checkDraw()) outputWinner("d")
            }
            game.changeMove()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField4(view: View) {
        if (bindingClass.imgField4.drawable == null) {
            if (game.movePlayer1) {
                bindingClass.imgField4.setImageResource(R.drawable.cross)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back_without_border)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back)
                game.arrFillFields[4] = "x"
                if (game.checkWinner()) outputWinner("x")
                else if(game.checkDraw()) outputWinner("d")
            }
            else {
                bindingClass.imgField4.setImageResource(R.drawable.zero)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back_without_border)
                game.arrFillFields[4] = "o"
                if (game.checkWinner()) outputWinner("o")
                else if(game.checkDraw()) outputWinner("d")
            }
            game.changeMove()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField5(view: View) {
        if (bindingClass.imgField5.drawable == null) {
            if (game.movePlayer1) {
                bindingClass.imgField5.setImageResource(R.drawable.cross)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back_without_border)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back)
                game.arrFillFields[5] = "x"
                if (game.checkWinner()) outputWinner("x")
                else if(game.checkDraw()) outputWinner("d")
            }
            else {
                bindingClass.imgField5.setImageResource(R.drawable.zero)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back_without_border)
                game.arrFillFields[5] = "o"
                if (game.checkWinner()) outputWinner("o")
                else if(game.checkDraw()) outputWinner("d")
            }
            game.changeMove()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField6(view: View) {
        if (bindingClass.imgField6.drawable == null) {
            if (game.movePlayer1) {
                bindingClass.imgField6.setImageResource(R.drawable.cross)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back_without_border)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back)
                game.arrFillFields[6] = "x"
                if (game.checkWinner()) outputWinner("x")
                else if(game.checkDraw()) outputWinner("d")
            }
            else {
                bindingClass.imgField6.setImageResource(R.drawable.zero)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back_without_border)
                game.arrFillFields[6] = "o"
                if (game.checkWinner()) outputWinner("o")
                else if(game.checkDraw()) outputWinner("d")
            }
            game.changeMove()
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField7(view: View) {
        if (bindingClass.imgField7.drawable == null) {
            if (game.movePlayer1) {
                bindingClass.imgField7.setImageResource(R.drawable.cross)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back_without_border)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back)
                game.arrFillFields[7] = "x"
                if (game.checkWinner()) outputWinner("x")
                else if(game.checkDraw()) outputWinner("d")
            }
            else {
                bindingClass.imgField7.setImageResource(R.drawable.zero)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back_without_border)
                game.arrFillFields[7] = "o"
                if (game.checkWinner()) outputWinner("o")
                else if(game.checkDraw()) outputWinner("d")
            }
            game.changeMove()
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField8(view: View) {
        if (bindingClass.imgField8.drawable == null) {
            if (game.movePlayer1) {
                bindingClass.imgField8.setImageResource(R.drawable.cross)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back_without_border)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back)
                game.arrFillFields[8] = "x"
                if (game.checkWinner()) outputWinner("x")
                else if(game.checkDraw()) outputWinner("d")

            }
            else {
                bindingClass.imgField8.setImageResource(R.drawable.zero)
                bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back)
                bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back_without_border)
                game.arrFillFields[8] = "o"
                if (game.checkWinner()) outputWinner("o")
                else if(game.checkDraw()) outputWinner("d")
            }
            game.changeMove()
        }
    }

    private fun clearGameField() {
        for (i in 0 until bindingClass.lGameField.childCount) {
            val l = bindingClass.lGameField.getChildAt(i) as? LinearLayout

            l?.let{
                for (j in 0 until it.childCount) {
                    val view = it.getChildAt(j)

                    if (view is ImageView) {
                        view.setImageDrawable(null)
                    }
                }
            }


        }

        bindingClass.lPlayer1.background = getDrawable(R.drawable.round_back)
        bindingClass.lPlayer2.background = getDrawable(R.drawable.round_back_without_border)
        game = TicTacToeP2()
    }

    private fun outputWinner(winner: String) {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog_box)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)

        val tvWinner = dialog.findViewById<TextView>(R.id.tvWinner)
        val imgWinner = dialog.findViewById<ImageView>(R.id.imgWinner)
        val btnPlayAgain = dialog.findViewById<Button>(R.id.btnPlayAgain)
        dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_bg))
        val btnExit = dialog.findViewById<Button>(R.id.btnExit)

        if (winner == "x") {
            tvWinner.text = getString(R.string.won) + " " + getString(R.string.player1)
            imgWinner.setImageResource(R.drawable.cross)
        }
        else if (winner == "o"){
            tvWinner.text = getString(R.string.won) + " " + getString(R.string.player2)
            imgWinner.setImageResource(R.drawable.zero)
        }

        else if (winner == "d") {
            tvWinner.text = getString(R.string.draw)
            imgWinner.setImageResource(R.drawable.draw)
        }

        btnPlayAgain.setOnClickListener{
            clearGameField()
            dialog.dismiss()
        }

        btnExit.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
            finish()
        }

        dialog.show()
    }



}