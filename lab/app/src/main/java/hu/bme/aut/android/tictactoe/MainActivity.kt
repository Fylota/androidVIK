package hu.bme.aut.android.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import hu.bme.aut.android.tictactoe.databinding.ActivityMainBinding
import hu.bme.aut.android.tictactoe.model.TicTacToeModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHighScore.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_highscore), Toast.LENGTH_LONG).show()
        }

        binding.btnStart.setOnClickListener {
            TicTacToeModel.resetModel()
            startActivity(Intent(this, GameActivity::class.java))
        }

        binding.btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}