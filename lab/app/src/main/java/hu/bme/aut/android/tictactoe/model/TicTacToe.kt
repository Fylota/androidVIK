package hu.bme.aut.android.tictactoe.model

object TicTacToeModel {

    const val EMPTY: Byte = 0
    const val CIRCLE: Byte = 1
    const val CROSS: Byte = 2

    var nextPlayer: Byte = CIRCLE

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

}
