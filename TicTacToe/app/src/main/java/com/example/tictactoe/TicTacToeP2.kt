package com.example.tictactoe

class TicTacToeP2 {
    var movePlayer1 = true
    var movePlayer2 = false
    val arrFillFields = Array(9) {""}

    fun changeMove() {
        movePlayer1 = !movePlayer1
        movePlayer2 = !movePlayer2
    }

    fun checkWinner(): Boolean {
        if (arrFillFields[0] == arrFillFields[1] && arrFillFields[1] == arrFillFields[2] && arrFillFields[0] != "") return true
        else if(arrFillFields[3] == arrFillFields[4] && arrFillFields[4] == arrFillFields[5] && arrFillFields[3] != "") return true
        else if(arrFillFields[6] == arrFillFields[7] && arrFillFields[7] == arrFillFields[8] && arrFillFields[6] != "") return true

        else if(arrFillFields[0] == arrFillFields[3] && arrFillFields[3] == arrFillFields[6] && arrFillFields[0] != "") return true
        else if(arrFillFields[1] == arrFillFields[4] && arrFillFields[4] == arrFillFields[7] && arrFillFields[1] != "") return true
        else if(arrFillFields[2] == arrFillFields[5] && arrFillFields[5] == arrFillFields[8] && arrFillFields[2] != "") return true

        else if(arrFillFields[0] == arrFillFields[4] && arrFillFields[4] == arrFillFields[8] && arrFillFields[0] != "") return true
        else return arrFillFields[2] == arrFillFields[4] && arrFillFields[4] == arrFillFields[6] && arrFillFields[2] != ""
    }

    fun checkDraw() = !arrFillFields.contains("")


}