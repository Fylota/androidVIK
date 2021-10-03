package hu.bme.aut.android.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import hu.bme.aut.android.tictactoe.model.TicTacToeModel.CIRCLE
import hu.bme.aut.android.tictactoe.model.TicTacToeModel.CROSS
import hu.bme.aut.android.tictactoe.model.TicTacToeModel.EMPTY

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }

    fun endGame(end: Byte) {
        when(end){
            EMPTY -> Toast.makeText(this, getString(R.string.toast_end_draw), Toast.LENGTH_LONG).show()
            CIRCLE -> Toast.makeText(this, getString(R.string.toast_end_circle), Toast.LENGTH_LONG).show()
            CROSS -> Toast.makeText(this, getString(R.string.toast_end_cross), Toast.LENGTH_LONG).show()
        }
    }
}