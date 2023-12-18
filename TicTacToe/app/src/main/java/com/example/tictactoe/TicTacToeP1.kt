package com.example.tictactoe

import kotlin.math.max
import kotlin.math.min



class TicTacToeP1 {
    val fieldGame = Array(3) {Array(3) {""} }
    var computer = ""
    var player = ""


    fun distributeSide(p: String) {
        player = p

        computer = if (p == "x") "o" else "x"

    }

    fun checkWinner(player: String): Boolean {
        for (i in 0..2) {
            if (fieldGame[i].all { it == player }) return true
            if ((0..2).all { j -> fieldGame[j][i] == player }) return true
        }

        if ((0..2).all { i -> fieldGame[i][i] == player} || (0..2).all { i -> fieldGame[i][2-i] == player }) return true
        return false
    }

    fun checkDraw(): Boolean {
        for (i in fieldGame) {
            if (i.contains("")) return false
        }

        return true
    }

    val isGameOver: Boolean get() = checkWinner(player) || checkWinner(computer) || checkDraw()

    val availableCells: List<Cell>
        get() {
            val cells = mutableListOf<Cell>()
            for (i in fieldGame.indices) {
                for (j in fieldGame.indices) {
                    if (fieldGame[i][j] == "") {
                        cells.add(Cell(i, j))
                    }
                }
            }
            return cells
        }

    var computersMove: Cell? = null

    fun minimax(depth: Int, p: String): Int {
        if(checkWinner(computer)) return +1
        if(checkWinner(player)) return -1
        if(checkDraw()) return 0

        var min = Integer.MAX_VALUE
        var max = Integer.MIN_VALUE

        for (i in availableCells.indices) {
            val cell = availableCells[i]

            if (p == computer) {
                placeMove(cell, computer)
                val currentScore = minimax(depth + 1, player)
                max = max(currentScore, max)

                if (currentScore >= 0) {
                    if (depth == 0) computersMove = cell
                }

                if (currentScore == 1) {
                    fieldGame[cell.i][cell.j] = ""
                    break
                }

                if (i == availableCells.size - 1 && max < 0) {
                    if (depth == 0) computersMove = cell
                }
            } else if (p == player) {
                placeMove(cell, player)
                val currentScore = minimax(depth + 1, computer)
                min = min(currentScore, min)

                if (min == -1) {
                    fieldGame[cell.i][cell.j] = ""
                    break
                }
            }
            fieldGame[cell.i][cell.j] = ""
        }

        return if(player == computer) max else min
    }

    fun placeMove(cell: Cell, p: String) {
        fieldGame[cell.i][cell.j] = p
    }
}