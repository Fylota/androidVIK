package hu.bme.aut.android.tictactoe.model

import hu.bme.aut.android.tictactoe.MainActivity

object TicTacToeModel {

    const val EMPTY: Byte = 0
    const val CIRCLE: Byte = 1
    const val CROSS: Byte = 2

    var nextPlayer: Byte = CIRCLE
    var winner: Byte = EMPTY

    private var model: Array<ByteArray> = arrayOf(
        byteArrayOf(EMPTY, EMPTY, EMPTY),
        byteArrayOf(EMPTY, EMPTY, EMPTY),
        byteArrayOf(EMPTY, EMPTY, EMPTY))

    fun resetModel() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                model[i][j] = EMPTY
            }
        }
    }

    fun getFieldContent(x: Int, y: Int): Byte {
        return model[x][y]
    }

    fun changeNextPlayer() {
        if (nextPlayer == CIRCLE) {
            nextPlayer = CROSS
        } else {
            nextPlayer = CIRCLE
        }
    }

    fun setFieldContent(x: Int, y: Int, content: Byte): Byte {
        changeNextPlayer()
        model[x][y] = content
        return content
    }

    fun checkFull(): Boolean{
        var counter = 0
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if(model[i][j] == EMPTY){
                    counter++
                }
            }
        }
        return counter == 0
    }

    fun checkEnd(): Boolean {
        if (checkFull()){
            winner = EMPTY
            return true
        }

        if (model[0][0] == model[1][1] && model[0][0] == model[2][2] && model[0][0]!=EMPTY){
            winner = getFieldContent(0,0)
            return true
        } else if (model[0][2] == model[1][1] && model[0][2] == model[2][0] && model[0][2]!=EMPTY) {
            winner = getFieldContent(0,2)
            return true
        }
        for (i in 0 until 3) {
            if (model[i][0] == model[i][1] && model[i][0] == model[i][2] && model[i][0]!=EMPTY) {
                winner = getFieldContent(i,0)
                return true
            } else if (model[0][i] == model[1][i] && model[0][i] == model[2][i] && model[0][i]!=EMPTY) {
                winner = getFieldContent(0,i)
                return true
            }
        }
        return false
    }

    @JvmName("getWinner1")
    fun getWinner(): Byte{
        return winner
    }

}
