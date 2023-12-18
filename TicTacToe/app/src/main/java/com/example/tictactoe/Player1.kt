package com.example.tictactoe

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.tictactoe.databinding.ActivityPlayer1Binding
import kotlin.random.Random

class Player1 : AppCompatActivity() {
    private lateinit var bindingClass: ActivityPlayer1Binding
    private lateinit var x_o: String
    private lateinit var dialog: Dialog
    private var game = TicTacToeP1()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityPlayer1Binding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val scale_up_field_game = AnimationUtils.loadAnimation(this, R.anim.increase_size)
        bindingClass.lGameField.startAnimation(scale_up_field_game)

        x_o = arrayOf("x", "o")[Random.nextInt(2)]
        chooseSide(x_o)
        game.distributeSide(x_o)

        makeFirstMove()

    }

    fun onClickBack(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onClickRestart(view: View) {
        clearGameField()
        x_o = arrayOf("x", "o")[Random.nextInt(2)]
        chooseSide(x_o)
        game = TicTacToeP1()
        game.distributeSide(x_o)

        makeFirstMove()

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
    }

    private fun chooseSide(side: String) {
        if (side == "x") {
            bindingClass.txtPlayer1.text = getString(R.string.player)
            bindingClass.txtPlayer2.text = getString(R.string.computer)
        }
        else {
            bindingClass.txtPlayer1.text = getString(R.string.computer)
            bindingClass.txtPlayer2.text = getString(R.string.player)
        }
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

        if (winner == x_o) {
            val text = getString(R.string.won) + " " + getString(R.string.player)
            tvWinner.text = text
            imgWinner.setImageResource(R.drawable.cross)
        } else if (winner == "d") {
            tvWinner.text = getString(R.string.draw)
            imgWinner.setImageResource(R.drawable.draw)
        } else {
            val text = getString(R.string.won) + " " + getString(R.string.computer)
            tvWinner.text = text
            imgWinner.setImageResource(R.drawable.cross)
        }

        btnPlayAgain.setOnClickListener{
            clearGameField()
            x_o = arrayOf("x", "o")[Random.nextInt(2)]
            chooseSide(x_o)
            game = TicTacToeP1()
            game.distributeSide(x_o)

            makeFirstMove()

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

    private fun mapBoardToIi() {
        for (i in game.fieldGame.indices) {
            for (j in game.fieldGame.indices) {


                when(game.fieldGame[i][j]) {
                    "x" -> {
                        val l = bindingClass.lGameField.getChildAt(i) as? LinearLayout
                        l?.let{
                            val view = it.getChildAt(j) as ImageView

                            if (view is ImageView) view.setImageDrawable(getDrawable(R.drawable.cross))
                        }
                    }
                    "o" -> {
                        val l = bindingClass.lGameField.getChildAt(i) as? LinearLayout
                        l?.let{
                            val view = it.getChildAt(j) as ImageView

                            if (view is ImageView) view.setImageDrawable(getDrawable(R.drawable.zero))
                        }
                    }
                    "" -> {
                        val l = bindingClass.lGameField.getChildAt(i) as? LinearLayout
                        l?.let{
                            val view = it.getChildAt(j) as ImageView

                            if (view is ImageView) view.setImageDrawable(null)
                        }
                    }
                }
            }
        }
    }

    private fun makeFirstMove() {
        if (x_o != "x") {
            game.minimax(0, game.computer)

            game.computersMove?.let {
                game.placeMove(it, game.computer)
            }

            mapBoardToIi()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField0(view: View) {
        if (bindingClass.imgField0.drawable == null) {
            if (!game.isGameOver) {
                val cell = Cell(0, 0)

                game.placeMove(cell, game.player)
                game.minimax(0, game.computer)

                game.computersMove?.let {
                    game.placeMove(it, game.computer)
                }

                mapBoardToIi()
            }

            when {
                game.checkWinner(x_o) -> outputWinner(game.player)
                game.checkWinner(game.computer) -> outputWinner(game.computer)
                game.checkDraw() -> outputWinner("d")
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField1(view: View) {
        if (bindingClass.imgField1.drawable == null) {
            if (!game.isGameOver) {
                val cell = Cell(0, 1)

                game.placeMove(cell, game.player)
                game.minimax(0, game.computer)

                game.computersMove?.let {
                    game.placeMove(it, game.computer)
                }

                mapBoardToIi()
            }

            when {
                game.checkWinner(x_o) -> outputWinner(game.player)
                game.checkWinner(game.computer) -> outputWinner(game.computer)
                game.checkDraw() -> outputWinner("d")
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField2(view: View) {
        if (bindingClass.imgField2.drawable == null) {
            if (!game.isGameOver) {
                val cell = Cell(0, 2)

                game.placeMove(cell, game.player)
                game.minimax(0, game.computer)

                game.computersMove?.let {
                    game.placeMove(it, game.computer)
                }

                mapBoardToIi()
            }

            when {
                game.checkWinner(x_o) -> outputWinner(game.player)
                game.checkWinner(game.computer) -> outputWinner(game.computer)
                game.checkDraw() -> outputWinner("d")
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField3(view: View) {
        if (bindingClass.imgField3.drawable == null) {
            if (!game.isGameOver) {
                val cell = Cell(1, 0)

                game.placeMove(cell, game.player)
                game.minimax(0, game.computer)

                game.computersMove?.let {
                    game.placeMove(it, game.computer)
                }

                mapBoardToIi()
            }

            when {
                game.checkWinner(x_o) -> outputWinner(game.player)
                game.checkWinner(game.computer) -> outputWinner(game.computer)
                game.checkDraw() -> outputWinner("d")
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField4(view: View) {
        if (bindingClass.imgField4.drawable == null) {
            if (!game.isGameOver) {
                val cell = Cell(1, 1)

                game.placeMove(cell, game.player)
                game.minimax(0, game.computer)

                game.computersMove?.let {
                    game.placeMove(it, game.computer)
                }

                mapBoardToIi()
            }

            when {
                game.checkWinner(x_o) -> outputWinner(game.player)
                game.checkWinner(game.computer) -> outputWinner(game.computer)
                game.checkDraw() -> outputWinner("d")
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField5(view: View) {
        if (bindingClass.imgField5.drawable == null) {
            if (!game.isGameOver) {
                val cell = Cell(1, 2)

                game.placeMove(cell, game.player)
                game.minimax(0, game.computer)

                game.computersMove?.let {
                    game.placeMove(it, game.computer)
                }

                mapBoardToIi()
            }

            when {
                game.checkWinner(x_o) -> outputWinner(game.player)
                game.checkWinner(game.computer) -> outputWinner(game.computer)
                game.checkDraw() -> outputWinner("d")
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField6(view: View) {
        if (bindingClass.imgField6.drawable == null) {
            if (!game.isGameOver) {
                val cell = Cell(2, 0)

                game.placeMove(cell, game.player)
                game.minimax(0, game.computer)

                game.computersMove?.let {
                    game.placeMove(it, game.computer)
                }

                mapBoardToIi()
            }

            when {
                game.checkWinner(x_o) -> outputWinner(game.player)
                game.checkWinner(game.computer) -> outputWinner(game.computer)
                game.checkDraw() -> outputWinner("d")
            }
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField7(view: View) {
        if (bindingClass.imgField7.drawable == null) {
            if (!game.isGameOver) {
                val cell = Cell(2, 1)

                game.placeMove(cell, game.player)
                game.minimax(0, game.computer)

                game.computersMove?.let {
                    game.placeMove(it, game.computer)
                }

                mapBoardToIi()
            }

            when {
                game.checkWinner(x_o) -> outputWinner(game.player)
                game.checkWinner(game.computer) -> outputWinner(game.computer)
                game.checkDraw() -> outputWinner("d")
            }
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickField8(view: View) {
        if (bindingClass.imgField8.drawable == null) {
            if (!game.isGameOver) {
                val cell = Cell(2, 2)

                game.placeMove(cell, game.player)
                game.minimax(0, game.computer)

                game.computersMove?.let {
                    game.placeMove(it, game.computer)
                }

                mapBoardToIi()
            }

            when {
                game.checkWinner(x_o) -> outputWinner(game.player)
                game.checkWinner(game.computer) -> outputWinner(game.computer)
                game.checkDraw() -> outputWinner("d")
            }
        }
    }


}